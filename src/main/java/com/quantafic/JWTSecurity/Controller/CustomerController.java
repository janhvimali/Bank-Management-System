package com.quantafic.JWTSecurity.Controller;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.Service.MasterManagementService;
import com.quantafic.JWTSecurity.Service.OfferGenartionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    OfferGenartionService offerGenartionService;

    @Autowired
    MasterManagementService masterManagementService;

    @GetMapping("/{applicationId}/offer")
    public ResponseEntity<ApiResponse<?>> getOffer(@PathVariable("applicationId") String id) {
        Object response = offerGenartionService.showOffer(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Offer fetched", response));
    }

    @PostMapping("/{applicationId}/accept")
    public ResponseEntity<ApiResponse<?>> acceptOffer(@PathVariable("applicationId") String id) {
        Object response = offerGenartionService.acceptOffer(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Offer accepted", response));
    }

    @PostMapping("/{applicationId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectOffer(@PathVariable("applicationId") String id) {
        Object response = offerGenartionService.rejectOffer(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Offer rejected", response));
    }

    @PostMapping("/{applicationId}/Esign")
    public ResponseEntity<ApiResponse<?>> esign(@PathVariable("applicationId") String id) {
        Object response = offerGenartionService.esigned(id , "ST-WNH5");
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Application e-signed", response));
    }

    @GetMapping("/master/loanproduct")
    public ResponseEntity<ApiResponse<?>> getAllLoanProduct() {
        Object response = masterManagementService.getAllLoanProducts();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Loan Products", response));
    }
}
