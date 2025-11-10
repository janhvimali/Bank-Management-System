package com.quantafic.JWTSecurity.Controller.UserController;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.DTO.UserResponseDTO;
import com.quantafic.JWTSecurity.Model.DtiMaster;
import com.quantafic.JWTSecurity.Model.FoirMaster;
import com.quantafic.JWTSecurity.Model.LoanProductMaster;
import com.quantafic.JWTSecurity.Model.Users;
import com.quantafic.JWTSecurity.Service.MasterManagementService;
import com.quantafic.JWTSecurity.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    MasterManagementService masterManagementService;

    @GetMapping
    public String greetAdmin() {
        return "Welcome to admin page";
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody Users user) {
        UserResponseDTO response = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, true, null, "Staff registered successfully!", response));
    }

    @PatchMapping("/{staffId}/update")
    public ResponseEntity<ApiResponse<?>> UpdateUser(@Valid @RequestBody UserResponseDTO userResponseDTO) {
        userService.editUser(userResponseDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(201, true, null, "Staff updated successfully!", null));
    }

    @GetMapping("users")
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        Object response = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "All users found", response));
    }

    @PostMapping("master/loan-product")
    public ResponseEntity<ApiResponse<?>> createLoanProduct(@Valid @RequestBody LoanProductMaster loanProductMaster) {
        Object response = masterManagementService.createLoanProduct(loanProductMaster);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, true, null, "Loan Product Created", response));
    }

    @PostMapping("/master/foir")
    public ResponseEntity<ApiResponse<?>> createFoirMaster(@Valid @RequestBody FoirMaster foirMaster) {
        Object response = masterManagementService.createFoirMaster(foirMaster);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, true, null, "Foir Master Created", response));
    }

    @PostMapping("/master/dti")
    public ResponseEntity<ApiResponse<?>> createDtiMaster(@Valid @RequestBody DtiMaster dtiMaster) {
        Object response = masterManagementService.createDtiMaster(dtiMaster);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, true, null, "DTI Master Created", response));
    }

    @GetMapping("/master/loanproduct")
    public ResponseEntity<ApiResponse<?>> getAllLoanProduct() {
        Object response = masterManagementService.getAllLoanProducts();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Loan Products", response));
    }

    @GetMapping("/master/dti")
    public ResponseEntity<ApiResponse<?>> getAlldti() {
        Object response = masterManagementService.getAllDtiMasters();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "DTI Masters", response));
    }

    @GetMapping("/master/foir")
    public ResponseEntity<ApiResponse<?>> getAllfoir() {
        Object response = masterManagementService.getAllFoirMasters();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Foir Masters", response));
    }

    @PatchMapping("master/foir/edit/{masterId}")
    public ResponseEntity<ApiResponse<?>> updateFoirMaster(@PathVariable("masterId") Long id, @Valid @RequestBody FoirMaster foirMaster) {
        Object response = masterManagementService.editFoirMaster(id, foirMaster);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Foir Master Updated", response));
    }

    @PatchMapping("master/dti/edit/{masterId}")
    public ResponseEntity<ApiResponse<?>> updatedtiMaster(@PathVariable("masterId") Long id, @Valid @RequestBody DtiMaster dtiMaster) {
        Object response = masterManagementService.editDtiMaster(id, dtiMaster);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "DTI Master Updated", response));
    }

    @PatchMapping("master/loanproduct/edit/{masterId}")
    public ResponseEntity<ApiResponse<?>> updateLoanProduct(@PathVariable("masterId") Long id, @Valid @RequestBody LoanProductMaster loanProductMaster) {
        Object response = masterManagementService.editLoanProduct(id, loanProductMaster);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Loan Product Updated", response));
    }
}

