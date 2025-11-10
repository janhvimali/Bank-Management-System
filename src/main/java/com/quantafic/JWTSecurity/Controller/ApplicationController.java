package com.quantafic.JWTSecurity.Controller;


import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.DTO.ApplicationIncomeDetailsDTO;
import com.quantafic.JWTSecurity.DTO.ApplicationLoanRequirementDTO;
import com.quantafic.JWTSecurity.DTO.RejectRequestDTO;
import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import com.quantafic.JWTSecurity.Enum.SystemDecision;
import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.CreditReport;
import com.quantafic.JWTSecurity.Service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController

@RequestMapping("/application")

public class ApplicationController {

    @Autowired
    ApplicationService applicationService;


    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getApplicationByCustomer() {
        Object response = applicationService.getApplicationsByCustomer();
        if (response != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Applications found", response));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(204, true, null, "No content found", response));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllApplication(){
        Object response = applicationService.getAllApplications();
        if (response != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Applications found", response));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(204, true, null, "No content found", response));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createApplication(
            @Valid @RequestBody ApplicationLoanRequirementDTO applicationLoanRequirementDTO) {

        Object response = applicationService.createApplication(applicationLoanRequirementDTO);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, true, null, "Application Created", response));
    }

    @PatchMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<?>> submitApplication(@PathVariable("id") String id) {
        Object response = applicationService.submitApplication(id, "ST-5LNA");
        return ResponseEntity.status(201).body(new ApiResponse<>(201, true, null, "Submitted", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getApplication(@PathVariable("id") String id) {
        Object response = applicationService.getApplication(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Application fetched", response));
    }

    @PatchMapping("/1/{applicationId}")
    public ResponseEntity<ApiResponse<?>> updateapplication(
            @PathVariable("applicationId") String id,
            @Valid @RequestBody ApplicationLoanRequirementDTO applicationLoanRequirementDTO) {

        try {
            Object response = applicationService.updateApplication(id, applicationLoanRequirementDTO);

            return ResponseEntity.ok(
                    new ApiResponse<>(200, true, null, "Application updated" , response)
            );
        } catch (Exception e) {
            return ResponseEntity.status(204).body(
                    new ApiResponse<>(204, false, e.getMessage(), "not found" ,null)
            );
        }
    }


    @PatchMapping("/2/{applicationId}")
    public ResponseEntity<ApiResponse<?>> updateIncomeDetails(
            @PathVariable("applicationId") String id,
            @Valid @RequestBody ApplicationIncomeDetailsDTO applicationIncomeDetailsDTO) {

        try {
            Object response = applicationService.setIncomeDetails(id, applicationIncomeDetailsDTO);

            return ResponseEntity.ok(
                    new ApiResponse<>(200, true, null, "Income details saved" , response)
            );
        } catch (Exception e) {
            return ResponseEntity.status(204).body(
                    new ApiResponse<>(204, false, e.getMessage(), "not found" ,null)
            );
        }
    }

    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectApplication(
            @PathVariable("applicationId") String id ,
            @Valid RejectRequestDTO rejectRequestDTO){

        try {
            Object response = applicationService.rejectApplication(id , rejectRequestDTO);

            return ResponseEntity.ok(
                    new ApiResponse<>(200, true, null, "Application rejected successfully" , response)
            );
        } catch (Exception e) {
            return ResponseEntity.status(204).body(
                    new ApiResponse<>(204, false, e.getMessage(), "not found" ,null)
            );
        }
    }
}
