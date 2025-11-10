package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.DTO.BureauResponseDTO;
import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import com.quantafic.JWTSecurity.Enum.ScoreProvider;
import com.quantafic.JWTSecurity.Enum.SystemDecision;
import com.quantafic.JWTSecurity.Exception.MissingDataException;
import com.quantafic.JWTSecurity.Model.*;
import com.quantafic.JWTSecurity.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
public class BureauServices {
    @Value("${bureau.api}")
    private String bureauApi;
    @Autowired
    private BureauRepository bureauRepository;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private FoirMasterRepository foirMasterRepository;
    @Autowired
    private DtiMasterRepository dtiMasterRepository;
    @Autowired
    private BureauSystemScoreMasterRepository bureauMasterRepo;
    @Autowired
    private EmploymentTypeSysScoreRepository employmentTypeSysScoreRepository;
    @Autowired
    private CreditReportsRepository creditReportsRepository;
    @Autowired
    private UserRepo userRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    // Fetch and save Bureau score for an application
    public Bureau fetchAndSaveScore(String applicationId) {
        Application application = applicationService.getApplicationEntity(applicationId);

        if (application.getBureau() != null) {
            return application.getBureau();
        }

        Bureau bureau = fetchBureauScoreFromStub(application);
        Bureau savedBureau = bureauRepository.save(bureau);
        application.setBureau(savedBureau);
        applicationService.saveApplication(application);
        return savedBureau;
    }

    private Bureau fetchBureauScoreFromStub(Application application) {
        int maxRetries = 20;
        int retryCount = 0;
        long waitMillis = 2000;
        while (retryCount < maxRetries) {
            try {
                BureauResponseDTO response = restTemplate.getForObject(
                        bureauApi,
                        BureauResponseDTO.class
                );

                if (isValidBureauResponse(response)) {
                    BureauResponseDTO.Score firstScore =
                            response.getConsumerCreditData().get(0).getScores().get(0);

                    return Bureau.builder()
                            .applicationId(application.getApplicationId())
                            .provider(ScoreProvider.CIBIL)
                            .score(Integer.parseInt(firstScore.getScore()))
                            .summaryJson(response.toString())
                            .referenceId("MOCK_REF_12345")
                            .pulledAt(Instant.now())
                            .build();
                }

                retryCount++;
                Thread.sleep(waitMillis);

            } catch (RestClientException | InterruptedException e) {
                retryCount++;
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException("Failed to fetch and save Bureau score after " + maxRetries + " attempts.");
    }

    private boolean isValidBureauResponse(BureauResponseDTO response) {
        return response != null &&
                response.getConsumerCreditData() != null &&
                response.getControlData() != null &&
                !response.getConsumerCreditData().isEmpty() &&
                !response.getConsumerCreditData().get(0).getScores().isEmpty() &&
                response.getControlData().isSuccess();
    }

    // Save Bureau and link to Application
    public Bureau saveBureau(String applicationId) {
        Bureau bureau = bureauRepository.findByApplicationId(applicationId);
        Application application = applicationService.getApplicationEntity(applicationId);
        application.setBureau(bureau);
        applicationService.saveApplication(application);
        return bureau;
    }

    // Get Bureau by bureau id
    public Bureau getBureau(String bureauId) {
        return bureauRepository.findById(bureauId)
                .orElseThrow(() -> new RuntimeException("No record found"));
    }

    // Get Bureau by application id
    public Bureau getBureauByApplicationId(String applicationId) {
        return bureauRepository.findByApplicationId(applicationId);
    }

    // Get Credit Report by credit Report Id
    public CreditReport getCreditReport(String creditReportId) {
        return creditReportsRepository.findById(creditReportId)
                .orElseThrow(() -> new RuntimeException("Credit Report not found"));
    }

    public CreditReport createReport(String applicationId){
        Application application = applicationService.getApplicationEntity(applicationId);
        Bureau bureau = bureauRepository.findByApplicationId(applicationId);
        CreditReport creditReport = creditReportsRepository.findByApplicationId(applicationId);
        if(creditReport == null ){
            Integer bureauScore  = bureau.getScore();
            BigDecimal emiObligation = application.getExistingEmiObligation();
            BigDecimal proposedEmi = application.getRequestedAmount().divide(BigDecimal.valueOf(application.getRequestedTenure()) , 4, RoundingMode.HALF_UP);
            BigDecimal netMonthlyIncome = application.getNetMonthlyIncome();
            BigDecimal grossMonthlyIncome = application.getGrossMonthlyIncome();
            BigDecimal foir = getFoir(emiObligation , proposedEmi , netMonthlyIncome);

            System.out.println("Proposed Emi: " + proposedEmi);
            System.out.println("net income: " + netMonthlyIncome);
            System.out.println("gross income: " + grossMonthlyIncome);
            System.out.println("emi obligation: " + emiObligation);
            System.out.println("foir:" + foir );
            BigDecimal dti = getDti(emiObligation , proposedEmi , grossMonthlyIncome);
            System.out.println("dti:" + foir );



            creditReport = CreditReport.builder()
                    .applicationId(applicationId)
                    .foir(foir)
                    .dti(dti)
                    .calculatedAt(Instant.now())
                    .BureauScore(bureauScore)
                    .bureau(bureau)
                    .systemScore(getSystemScore(applicationId , foir , dti , bureauScore , application.getCustomer().getEmploymentType() ))
                    .systemDecision(SystemDecision.PENDING)
                    .build();

            CreditReport savedCreditReport = creditReportsRepository.save(creditReport);

            application.setCreditReport(savedCreditReport);
            applicationService.saveApplication(application);

            return savedCreditReport;
        }

        return creditReport;




    }

    //Decision APPROVED
    public String creditAnalystDecisionApprove(String creditReportId){
        CreditReport creditReport = creditReportsRepository.findById(creditReportId).orElseThrow(()-> new RuntimeException("Credit report not found"));
        creditReport.setSystemDecision(SystemDecision.APPROVED);
        creditReportsRepository.save(creditReport);

        return "APPROVED";
    }

    //Decision Reject
    public String creditAnalystDecisionReject(String applicationId){
        CreditReport creditReport = creditReportsRepository.findByApplicationId(applicationId);
        Application application = applicationService.getApplicationEntity(applicationId);
        application.setStatus(ApplicationStatus.REJECTED);
        creditReport.setSystemDecision(SystemDecision.REJECTED);
        creditReportsRepository.save(creditReport);

        return "Rejected";
    }

    // Get credit report by application id
    public CreditReport getCreditReportByApplicationId(String applicationId) {
        return creditReportsRepository.findByApplicationId(applicationId);
    }

    // Assign application to next user and update status
    public String sendToNext(String applicationId, String nextUserId) {
        Application application = applicationService.getApplicationByIdForUser(applicationId);
        if(application.getCreditReport() == null){
            throw new MissingDataException("Credit report not found for this application");
        }
        if(application.getCreditReport() == null){
            throw new MissingDataException("Credit report not found for this application");
        }
        Users user = userRepo.findById(nextUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        application.setAssignedTo(user);
        application.setStatus(ApplicationStatus.APPROVED);
        applicationService.saveApplication(application);
        return "Sent to credit manager";
    }

    // FOIR Logic
    public BigDecimal getFoir(BigDecimal emiObligation, BigDecimal proposedEmi, BigDecimal netMonthlyIncome) {
        BigDecimal foir = (emiObligation.add(proposedEmi))
                .divide(netMonthlyIncome, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return foir.setScale(2, RoundingMode.HALF_UP);
    }

    // DTI Logic
    public BigDecimal getDti(BigDecimal emiObligation, BigDecimal proposedEmi, BigDecimal grossMonthlyIncome) {
        BigDecimal dti = (emiObligation.add(proposedEmi))
                .divide(grossMonthlyIncome, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return dti.setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate system score
    public BigDecimal getSystemScore(String applicationId, BigDecimal foir, BigDecimal dti, Integer bureauScore, String employmentType) {
        FoirMaster foirMaster = foirMasterRepository.findByFoir(foir)
                .orElseThrow(() -> new RuntimeException("Foir Master not found"));
        DtiMaster dtiMaster = dtiMasterRepository.findByDti(dti)
                .orElseThrow(() -> new RuntimeException("DTI Master not found"));
        BureauSystemScoreMaster bureauSystemScoreMaster = bureauMasterRepo.findByScore(bureauScore)
                .orElseThrow(() -> new RuntimeException("Bureau Master not found"));
        EmploymentTypeSystemScoreMaster employmentTypeSystemScoreMaster = employmentTypeSysScoreRepository.findByemploymentType(employmentType);

        BigDecimal systemScore = foirMaster.getSysPoints()
                .add(dtiMaster.getSysPoints())
                .add(bureauSystemScoreMaster.getSysPoints())
                .add(employmentTypeSystemScoreMaster.getSysPoints());

        return systemScore.setScale(2, RoundingMode.HALF_UP);
    }
}