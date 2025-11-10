package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.DTO.EditLoanOfferDTO;
import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import com.quantafic.JWTSecurity.Enum.CustomerDecision;
import com.quantafic.JWTSecurity.Enum.LoanProduct;
import com.quantafic.JWTSecurity.Exception.MissingDataException;
import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.LoanOffers;
import com.quantafic.JWTSecurity.Model.LoanProductMaster;
import com.quantafic.JWTSecurity.Model.Users;
import com.quantafic.JWTSecurity.Repo.LoanOffersRepository;
import com.quantafic.JWTSecurity.Repo.LoanProductsRepository;
import com.quantafic.JWTSecurity.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
public class OfferGenartionService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private LoanProductsRepository loanProductsRepository;

    @Autowired
    private LoanOffersRepository loanOffersRepository;

    @Autowired
    private ApplicationService applicationService; // Use service, not repo

    @Autowired
    private UserRepo userRepo;

    public LoanOffers generateOffer(String applicationId) {
        Application application = applicationService.getApplicationByIdForUser(applicationId);

        System.out.println("before");
        LoanOffers existingOffer = loanOffersRepository.findByApplicationId(applicationId);
        System.out.println("after");

        if (existingOffer != null) {
            return existingOffer;
        }

        System.out.println("1");

        application.setStatus(ApplicationStatus.APPROVED);
        LoanProductMaster loanProductMaster = loanProductsRepository.findByLoanProduct(application.getLoanProduct());

        System.out.println("2");

        boolean isAmountValid = verifyRequestedAmount(application.getRequestedAmount() , loanProductMaster);
        boolean isTenureValid = verifyTenure(application.getRequestedTenure(), loanProductMaster);

        System.out.println(application.getRequestedAmount());
        BigDecimal offerAmount = isAmountValid
                ? application.getRequestedAmount()
                : loanProductMaster.getMaxLoanAmount();

        System.out.println("3");

        System.out.println(offerAmount);

        int tenure = isTenureValid
                ? application.getRequestedTenure()
                : loanProductMaster.getMaxTenure();

        LoanOffers loanOffers = getOffer(
                application,
                offerAmount,
                loanProductMaster.getInterestRate(),
                tenure,
                loanProductMaster
        );
        System.out.println("5");


        LoanOffers savedOffer = loanOffersRepository.save(loanOffers);
        application.setLoanOffer(savedOffer);

        System.out.println("6");

        return loanOffers;
    }

    public LoanOffers editOffer(String applicationId, EditLoanOfferDTO editLoanOfferDTO) {
        Application application = applicationService.getApplicationByIdForUser(applicationId);
        LoanProductMaster loanProductMaster = loanProductsRepository.findByLoanProduct(application.getLoanProduct());
        LoanOffers existingOffer = loanOffersRepository.findByApplicationId(applicationId);

        BigDecimal principalAmount = editLoanOfferDTO.getLoanPrincipal();
        int loanTenure = editLoanOfferDTO.getLoanTenure();

        boolean isAmountValid = verifyRequestedAmount(principalAmount, loanProductMaster);
        boolean isTenureValid = verifyTenure(loanTenure, loanProductMaster);

        if (!isAmountValid) {
            throw new RuntimeException("Principal amount out of allowed range for this loan product");
        }

        if (!isTenureValid) {
            throw new RuntimeException("Loan tenure out of allowed range for this loan product");
        }

        LoanOffers newOffer = getOffer(application, principalAmount, loanProductMaster.getInterestRate(), loanTenure, loanProductMaster);

        existingOffer.setOfferAmount(newOffer.getOfferAmount());
        existingOffer.setTenure(newOffer.getTenure());
        existingOffer.setCreatedAt(Instant.now());
        existingOffer.setEmiAmount(newOffer.getEmiAmount());
        existingOffer.setInsurance(newOffer.getInsurance());
        existingOffer.setInterest(newOffer.getInterest());
        existingOffer.setNetPayable(newOffer.getNetPayable());
        existingOffer.setNetDisbursal(newOffer.getNetDisbursal());
        existingOffer.setProcessingFee(newOffer.getProcessingFee());

        loanOffersRepository.save(existingOffer);
        return existingOffer;
    }


    public LoanOffers updateOffer(String applicationId, BigDecimal newAmount, Integer newTenure) {
        LoanOffers existingOffer = loanOffersRepository.findByApplicationId(applicationId);

        if (newAmount != null) {
            existingOffer.setOfferAmount(newAmount);
        }
        if (newTenure != null) {
            existingOffer.setTenure(newTenure);
        }

        loanOffersRepository.save(existingOffer);
        return existingOffer;
    }

    public String sentToCustomer(String applicationId) {
        Application application = applicationService.getApplicationByIdForUser(applicationId);

        LoanOffers loanOffer = loanOffersRepository.findByApplicationId(applicationId);
        if(loanOffer == null){
            throw new MissingDataException("Loan Offer not existed");
        }
        loanOffer.setIsSend(true);
        application.setStatus(ApplicationStatus.OFFERED);
        emailService.sendLoanOfferEmail(application.getCustomer() , application);
        loanOffersRepository.save(loanOffer);
        applicationService.saveApplication(application);

        return "sent";
    }

    public String acceptOffer(String id) {
        LoanOffers loanOffers = loanOffersRepository.findByApplicationId(id);
        if(loanOffers == null){
            throw new MissingDataException("loan offer not found");
        }
        Application application = applicationService.getApplicationEntity(id);
        application.setStatus(ApplicationStatus.ESIGN);
        loanOffers.setCustDecision(CustomerDecision.ACCEPT);
        emailService.sendLoanOfferAcceptedEmail(application.getCustomer() , application , loanOffers);
        loanOffersRepository.save(loanOffers);
        application.setLoanOffer(loanOffers);
        applicationService.saveApplication(application);
        return "Accepted";
    }

    public String rejectOffer(String id) {
        Application application = applicationService.getApplicationEntity(id);
        LoanOffers loanOffers = loanOffersRepository.findByApplicationId(id);
        loanOffers.setCustDecision(CustomerDecision.REJECT);
        loanOffers.setIsSend(false);
        application.setStatus(ApplicationStatus.DECLINED);
        emailService.sendLoanOfferRejectedEmail(application.getCustomer() , application , loanOffers);
        loanOffersRepository.save(loanOffers);
        return "Rejected";
    }

    public String esigned(String id , String bankManagerStaffId) {
        Application application = applicationService.getApplicationEntity(id);
        if(application.getLoanOffer().getCustDecision() != CustomerDecision.ACCEPT){
            throw new MissingDataException("Cannot E-signed before Offer acceptance");
        }
        Users bankManager = userRepo.findById(bankManagerStaffId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        application.setESigned(true);
        application.setAssignedTo(bankManager);
        application.setStatus(ApplicationStatus.SIGNED);
        emailService.sendAgreementSignedEmail(application.getCustomer() , application);
        applicationService.saveApplication(application);
        return "Application Successfully E-Signed";
    }

    public LoanOffers getOfferByApplicationId(String applicationId){
        LoanOffers loanOffers = loanOffersRepository.findByApplicationId(applicationId);

        return loanOffers;
    }

    private boolean verifyRequestedAmount(BigDecimal requestedAmount, LoanProductMaster loanProductMaster) {
        return requestedAmount.compareTo(loanProductMaster.getMinLoanAmount()) >= 0 &&
                requestedAmount.compareTo(loanProductMaster.getMaxLoanAmount()) <= 0;
    }

    private boolean verifyTenure(int requestedTenure, LoanProductMaster loanProductMaster) {
        return requestedTenure >= loanProductMaster.getMinTenure() &&
                requestedTenure <= loanProductMaster.getMaxTenure();
    }

    private BigDecimal getEmi(BigDecimal principal, BigDecimal annualRate, int tenureMonths) {
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusRPowerN = BigDecimal.ONE.add(monthlyRate).pow(tenureMonths);
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(onePlusRPowerN);
        BigDecimal denominator = onePlusRPowerN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcInsurance(BigDecimal principal, BigDecimal insurancePercent) {
        return principal.multiply(insurancePercent)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcProcessing(BigDecimal principal, BigDecimal processingFeePercent) {
        return principal.multiply(processingFeePercent)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcDisbursal(BigDecimal principal, BigDecimal insurance, BigDecimal processingFee) {
        return principal.subtract(processingFee).subtract(insurance);
    }

    private LoanOffers getOffer(Application application, BigDecimal offerAmount, BigDecimal annualRate, int tenureMonths, LoanProductMaster loanProductMaster) {
        BigDecimal emi = getEmi(offerAmount, annualRate, tenureMonths);
        BigDecimal totalPayable = emi.multiply(BigDecimal.valueOf(tenureMonths));
        BigDecimal totalInterest = totalPayable.subtract(offerAmount);

        BigDecimal insurance = calcInsurance(offerAmount, loanProductMaster.getInsurance());
        BigDecimal processingFee = calcProcessing(offerAmount, loanProductMaster.getProcessingFees());
        BigDecimal netDisbursal = calcDisbursal(offerAmount, insurance, processingFee);

        return LoanOffers.builder()
                .loanProductId(loanProductMaster.getLoanProductId())
                .roi(loanProductMaster.getInterestRate())
                .offerAmount(offerAmount)
                .emiAmount(emi)
                .netPayable(totalPayable)
                .interest(totalInterest)
                .tenure(tenureMonths)
                .insurance(insurance)
                .processingFee(processingFee)
                .netDisbursal(netDisbursal)
                .applicationId(application.getApplicationId())
                .createdAt(Instant.now())
                .custDecision(CustomerDecision.PENDING)
                .isSend(false)
                .build();
    }

    public LoanOffers showOffer(String id) {
        return loanOffersRepository.findByApplicationId(id);
    }
}
