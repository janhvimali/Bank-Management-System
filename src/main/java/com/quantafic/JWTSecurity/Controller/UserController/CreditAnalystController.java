package com.quantafic.JWTSecurity.Controller.UserController;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.Model.CreditReport;
import com.quantafic.JWTSecurity.Service.ApplicationService;
import com.quantafic.JWTSecurity.Service.BureauServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creditanalyst")
public class CreditAnalystController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    BureauServices bureauServices;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getApplications() {
        Object response = applicationService.getApplicationsByUser();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Applications found", response));
    }

    @GetMapping("/{applicationId}/credit-report")
    public ResponseEntity<ApiResponse<?>> getCreditReport(@PathVariable("applicationId") String applicationId) {
        CreditReport response = bureauServices.createReport(applicationId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Credit report generated", response));
    }

    @PostMapping("/{reportId}/approve")
    public ResponseEntity<ApiResponse<?>> approveReport(@PathVariable("reportId") String reportId) {
        Object response = bureauServices.creditAnalystDecisionApprove(reportId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "APPROVED", response));
    }

    @PostMapping("/{reportId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectReport(@PathVariable("reportId") String reportId) {
        Object response = bureauServices.creditAnalystDecisionReject(reportId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Rejected", response));
    }

    @PostMapping("/{applicationId}/next")
    public ResponseEntity<ApiResponse<?>> sendToNext(@PathVariable("applicationId") String applicationId) {
        Object response = bureauServices.sendToNext(applicationId , "ST-4MES");
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "APPROVED and sent to next", response));
    }
}
