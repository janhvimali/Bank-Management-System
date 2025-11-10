package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.DTO.DisbursementDTO;
import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import com.quantafic.JWTSecurity.Exception.MissingDataException;
import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.Disbursement;
import com.quantafic.JWTSecurity.Repo.ApplicationRepo;
import com.quantafic.JWTSecurity.Repo.DisbursementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DisbursementService {
    @Autowired
    ApplicationService applicationService;

    @Autowired
    DisbursementRepo disbursementRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    OfferGenartionService offerGenartionService;

    //DisburseLoan to customer
    public Disbursement disburseLoan(String applicationId , DisbursementDTO disbursementDTO){
        Application application = applicationService.getApplicationByIdForUser(applicationId);
        if(!application.getESigned()){
            throw new MissingDataException("You cannot disburse before Esign");
        }
        Disbursement disbursement = Disbursement.builder()
                .bankAccountNumber(disbursementDTO.getBankAccountNo())
                .ifsc(disbursementDTO.getIfsc())
                .beneficiaryName(disbursementDTO.getBeneficiaryName())
                .disbursementAmount(application.getLoanOffer().getNetDisbursal())
                .disbursementMode(disbursementDTO.getMode())
                .application(application)
                .disbursalDate(Instant.now())
                .build();
        application.setStatus(ApplicationStatus.DISBURSED);
        emailService.sendLoanDisbursedEmail(application.getCustomer() , application , disbursement.getDisbursementAmount());
        application.setAssignedTo(null);
        System.out.println(disbursement);
        return disbursementRepo.save(disbursement);
    }
}
