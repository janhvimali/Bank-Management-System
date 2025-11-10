package com.quantafic.JWTSecurity.Controller.UserController;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.DTO.EditLoanOfferDTO;
import com.quantafic.JWTSecurity.Service.ApplicationService;
import com.quantafic.JWTSecurity.Service.OfferGenartionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creditmanager")
public class CreditManagerController {
    @Autowired
    OfferGenartionService offerGenartionService;
    @Autowired
    ApplicationService applicationService;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getApplications() {
        Object response = applicationService.getApplicationsByUser();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Applications found", response));
    }

    @GetMapping("/{applicationId}/offer")
    public ResponseEntity<ApiResponse<?>> getOffer(@PathVariable("applicationId") String id) {
        Object response = offerGenartionService.generateOffer(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Generated Offer", response));
    }

    @PostMapping("/{applicationId}/offer/edit")
    public ResponseEntity<ApiResponse<?>> editOffer(@PathVariable("applicationId") String id,
                                                   @Valid @RequestBody EditLoanOfferDTO editLoanOfferDTO) {
        Object response = offerGenartionService.editOffer(id, editLoanOfferDTO);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Offer Edited", response));
    }

    @PostMapping("/{applicationId}/offer/send")
    public ResponseEntity<ApiResponse<?>> sentToCustomer(@PathVariable("applicationId") String id) {
        Object response = offerGenartionService.sentToCustomer(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Sent to customer", response));
    }
}
