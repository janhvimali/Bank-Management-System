package com.quantafic.JWTSecurity.Controller.UserController;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.Service.ApplicationService;
import com.quantafic.JWTSecurity.Service.BureauServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankstaff")
public class BankStaffController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private BureauServices bureauServices;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getApplications() {
        Object response = applicationService.getApplicationsByUser();
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, null, "Applications found", response)
        );
    }

    @PostMapping("/{applicationId}/next")
    public ResponseEntity<ApiResponse<?>> sendToNext(@PathVariable("applicationId") String id) {
        String response = applicationService.sendToCreditAnalyst(id , "ST-8AW7");
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, null, response, null)
        );
    }
}
