package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.DTO.*;
import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.Customers;
import com.quantafic.JWTSecurity.Model.Users;
import com.quantafic.JWTSecurity.Repo.ApplicationRepo;
import com.quantafic.JWTSecurity.Repo.Customer_profileRepository;
import com.quantafic.JWTSecurity.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.security.access.AccessDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.quantafic.JWTSecurity.Enum.ApplicationStatus.DRAFT;
import static com.quantafic.JWTSecurity.Enum.ApplicationStatus.SUBMITTED;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private Customer_profileRepository customerProfileRepository;
    @Autowired
    private EmailService emailService;

    private final RestTemplate restTemplate = new RestTemplate();

    //Testing*************************************


    public Application getApplicationByIdForUser(String appId) {
        String staffId = userService.getIdFromToken();
        Application app = applicationRepo.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (app.getAssignedTo() == null || !app.getAssignedTo().getStaffId().equals(staffId)) {
            throw new AccessDeniedException("You are not authorized to access this application");
        }

        return app;
    }




    //********************************************

    // Get Application entity by ID
    public Application getApplicationEntity(String applicationId) {
        return applicationRepo.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    //  Save Application entity
    public Application saveApplication(Application application) {
        return applicationRepo.save(application);
    }

    // Delete Application
    @Transactional
    public void deleteApplication(String applicationId) {
        applicationRepo.deleteById(applicationId);
    }

    // Create Application
    public ApplicationDTO createApplication(ApplicationLoanRequirementDTO dto) {
        String customerId = userService.getIdFromToken();
        Customers customerProfile = customerProfileRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        Application application = Application.builder()
            .customer(customerProfile)
            .loanProduct(dto.getLoanProduct())
            .requestedAmount(dto.getRequestedAmount())
            .requestedTenure(dto.getRequestedTenure())
            .loanPurpose(dto.getLoanPurpose())
            .status(DRAFT)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

        Application savedApplication = applicationRepo.save(application);

        emailService.sendApplicationCreatedEmail(customerProfile, savedApplication);

        return mapToDTO(savedApplication);
    }

    // Update Application
    public ApplicationDTO updateApplication(String id, ApplicationLoanRequirementDTO dto) {
        Application application = getApplicationEntity(id);
        application.setLoanProduct(dto.getLoanProduct());
        application.setRequestedAmount(dto.getRequestedAmount());
        application.setRequestedTenure(dto.getRequestedTenure());
        application.setLoanPurpose(dto.getLoanPurpose());
        application.setUpdatedAt(Instant.now());
        Application updated = applicationRepo.save(application);
        return mapToDTO(updated);
    }

    // Set Income Details
    public ApplicationDTO setIncomeDetails(String applicationId, ApplicationIncomeDetailsDTO dto) {
        Application application = getApplicationEntity(applicationId);
        application.setExistingEmiObligation(dto.getExistingEmiObligation());
        application.setGrossMonthlyIncome(dto.getGrossMonthlyIncome());
        application.setNetMonthlyIncome(dto.getNetMonthlyIncome());
        application.setUpdatedAt(Instant.now());
        Application updated = applicationRepo.save(application);
        return mapToDTO(updated);
    }

    // Submit Application
    public ApplicationDTO submitApplication(String applicationId, String assignedToStaffId) {
        Application application = getApplicationEntity(applicationId);
        Users assignedTo = userRepo.findById(assignedToStaffId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        application.setStatus(SUBMITTED);
        application.setAssignedTo(assignedTo);
        application.setUpdatedAt(Instant.now());
        Application updated = applicationRepo.save(application);
        return mapToDTO(updated);
    }

    // Get all applications (as DTOs)
    public List<ApplicationDTO> getAllApplications() {
        return applicationRepo.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Get specific application
    public ApplicationDTO getApplication(String id) {
        Application application = getApplicationEntity(id);
        return mapToDTO(application);
    }

    // Get applications by customer (as DTOs)
    public List<ApplicationDTO> getApplicationsByCustomer() {
        String customerId = userService.getIdFromToken();
        Customers customer = customerProfileRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        return applicationRepo.findByCustomer(customer).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Get applications by assigned user (as DTOs)
    public List<ApplicationDTO> getApplicationsByUser() {
        String staffId = userService.getIdFromToken();
        Users user = userRepo.findById(staffId)
            .orElseThrow(() -> new RuntimeException("No user found"));
        return applicationRepo.findByAssignedTo(user).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    // Send to Credit Analyst
    public String sendToCreditAnalyst(String applicationId, String analystStaffId) {

        Application application = getApplicationByIdForUser(applicationId);


        RestTemplate restTemplate = new RestTemplate();
        String url = "http://172.16.0.114:8080/api/documents/status/" + application.getCustomer().getCustomerId();


        DocumentVerificationDTO response = restTemplate.getForObject(url, DocumentVerificationDTO.class);
        boolean verified = response.getData().isVerified();

        if (!verified) {
            throw new RuntimeException("Cannot forward: documents are not verified yet.");
        }


        Users analyst = userRepo.findById(analystStaffId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        application.setAssignedTo(analyst);
        application.setStatus(ApplicationStatus.VERIFIED);
        application.setUpdatedAt(Instant.now());
        applicationRepo.save(application);
        emailService.sendDocumentVerifiedEmail(application.getCustomer() , application);

        return "Forward successful";
    }


    // Reject Aplication
    public String rejectApplication(String applicationId , RejectRequestDTO rejectRequestDTO){

        Application application = getApplicationByIdForUser(applicationId);
        Customers customerProfile = customerProfileRepository.findById(application.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        application.setAssignedTo(null);
        application.setStatus(ApplicationStatus.REJECTED);
        emailService.sendLoanRejectionEmail(customerProfile , application , rejectRequestDTO.getMessage());
        return "rejected";
    }

    // Application -> ApplicationDTO
    private ApplicationDTO mapToDTO(Application application) {
        return ApplicationDTO.builder()
            .applicationId(application.getApplicationId())
            .loanProduct(application.getLoanProduct())
            .requestedAmount(application.getRequestedAmount())
            .requestedTenure(application.getRequestedTenure())
            .loanPurpose(application.getLoanPurpose())
            .preferredEmiDate(application.getPreferredEmiDate())
            .existingEmiObligation(application.getExistingEmiObligation())
            .grossMonthlyIncome(application.getGrossMonthlyIncome())
            .netMonthlyIncome(application.getNetMonthlyIncome())
            .status(application.getStatus())
            .branchCode(application.getBranchCode())
            .customerId(application.getCustomer() != null ? application.getCustomer().getCustomerId() : null)
            .bureauId(application.getBureau() != null ? application.getBureau().getBureauId() : null)
                .loanOffers(application.getLoanOffer() != null ? application.getLoanOffer() : null)
            .build();
    }

}
