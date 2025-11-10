package com.quantafic.JWTSecurity.Controller.UserController;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.DTO.DisbursementDTO;
import com.quantafic.JWTSecurity.Service.ApplicationService;
import com.quantafic.JWTSecurity.Service.DisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-officer")
public class LoanOfficerController {
    @Autowired
    ApplicationService applicationService;

    @Autowired
    DisbursementService disbursementService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getApplications() {
        Object response = applicationService.getApplicationsByUser();
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, null, "Applications found", response)
        );
    }

    @PostMapping("{applicationId}")
    public ResponseEntity<ApiResponse<?>> disburseLoan(@PathVariable("applicationId") String id ,@RequestBody DisbursementDTO disbursementDTO){
        Object response = disbursementService.disburseLoan(id , disbursementDTO);
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, null, "Applications found", response)
        );
    }

}
