package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.Model.DtiMaster;
import com.quantafic.JWTSecurity.Model.FoirMaster;
import com.quantafic.JWTSecurity.Model.LoanProductMaster;
import com.quantafic.JWTSecurity.Repo.DtiMasterRepository;
import com.quantafic.JWTSecurity.Repo.EmploymentTypeSysScoreRepository;
import com.quantafic.JWTSecurity.Repo.FoirMasterRepository;
import com.quantafic.JWTSecurity.Repo.LoanProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MasterManagementService {
    @Autowired
    private FoirMasterRepository foirMasterRepository;
    @Autowired
    private DtiMasterRepository dtiMasterRepository;
    @Autowired
    private EmploymentTypeSysScoreRepository employmentTypeSysScoreRepository;
    @Autowired
    private LoanProductsRepository loanProductsRepository;

    // Loan Product Master CRUD
    public LoanProductMaster createLoanProduct(LoanProductMaster loanProductMaster) {
        LoanProductMaster entity = LoanProductMaster.builder()
                .loanProduct(loanProductMaster.getLoanProduct())
                .productName(loanProductMaster.getProductName())
                .interestRate(loanProductMaster.getInterestRate())
                .minLoanAmount(loanProductMaster.getMinLoanAmount())
                .maxLoanAmount(loanProductMaster.getMaxLoanAmount())
                .minTenure(loanProductMaster.getMinTenure())
                .maxTenure(loanProductMaster.getMaxTenure())
                .insurance(loanProductMaster.getInsurance())
                .processingFees(loanProductMaster.getProcessingFees())
                .description(loanProductMaster.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        return loanProductsRepository.save(entity);
    }

    public List<LoanProductMaster> getAllLoanProducts() {
        return loanProductsRepository.findAll();
    }

    public LoanProductMaster getLoanProduct(Long id) {
        return loanProductsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan product not found"));
    }

    public LoanProductMaster editLoanProduct(Long id, LoanProductMaster newMaster) {
        LoanProductMaster master = getLoanProduct(id);
        master.setLoanProduct(newMaster.getLoanProduct());
        master.setProductName(newMaster.getProductName());
        master.setInterestRate(newMaster.getInterestRate());
        master.setMinLoanAmount(newMaster.getMinLoanAmount());
        master.setMaxLoanAmount(newMaster.getMaxLoanAmount());
        master.setMinTenure(newMaster.getMinTenure());
        master.setMaxTenure(newMaster.getMaxTenure());
        master.setInsurance(newMaster.getInsurance());
        master.setProcessingFees(newMaster.getProcessingFees());
        master.setDescription(newMaster.getDescription());
        master.setUpdatedAt(Instant.now());
        return loanProductsRepository.save(master);
    }

    // FOIR Master CRUD
    public FoirMaster createFoirMaster(FoirMaster foirMaster) {
        FoirMaster entity = FoirMaster.builder()
                .upperLimit(foirMaster.getUpperLimit())
                .lowerLimit(foirMaster.getLowerLimit())
                .riskLevel(foirMaster.getRiskLevel())
                .sysPoints(foirMaster.getSysPoints())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        return foirMasterRepository.save(entity);
    }

    public List<FoirMaster> getAllFoirMasters() {
        return foirMasterRepository.findAll();
    }

    public FoirMaster getFoirMaster(Long id) {
        return foirMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foir master not found"));
    }

    public FoirMaster editFoirMaster(Long id, FoirMaster newMaster) {
        FoirMaster master = getFoirMaster(id);
        master.setUpperLimit(newMaster.getUpperLimit());
        master.setLowerLimit(newMaster.getLowerLimit());
        master.setRiskLevel(newMaster.getRiskLevel());
        master.setSysPoints(newMaster.getSysPoints());
        master.setUpdatedAt(Instant.now());
        return foirMasterRepository.save(master);
    }

    // DTI Master CRUD
    public DtiMaster createDtiMaster(DtiMaster dtiMaster) {
        DtiMaster entity = DtiMaster.builder()
                .lowerLimit(dtiMaster.getLowerLimit())
                .upperLimit(dtiMaster.getUpperLimit())
                .riskLevel(dtiMaster.getRiskLevel())
                .sysPoints(dtiMaster.getSysPoints())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        return dtiMasterRepository.save(entity);
    }

    public List<DtiMaster> getAllDtiMasters() {
        return dtiMasterRepository.findAll();
    }

    public DtiMaster getDtiMaster(Long id) {
        return dtiMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DTI master not found"));
    }

    public DtiMaster editDtiMaster(Long id, DtiMaster newMaster) {
        DtiMaster master = getDtiMaster(id);
        master.setLowerLimit(newMaster.getLowerLimit());
        master.setUpperLimit(newMaster.getUpperLimit());
        master.setRiskLevel(newMaster.getRiskLevel());
        master.setSysPoints(newMaster.getSysPoints());
        master.setUpdatedAt(Instant.now());
        return dtiMasterRepository.save(master);
    }

    

}
