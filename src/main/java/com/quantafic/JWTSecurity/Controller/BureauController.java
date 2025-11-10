package com.quantafic.JWTSecurity.Controller;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.Model.Bureau;
import com.quantafic.JWTSecurity.Service.BureauServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bureau")
public class BureauController {

    @Autowired
    private BureauServices bureauServices;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getScore(@PathVariable("id") String id) {
        Bureau response = bureauServices.fetchAndSaveScore(id);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Bureau score fetched", response));
    }

    @GetMapping("/by-application/{applicationId}")
    public ResponseEntity<ApiResponse<?>> getBureauByApplication(@PathVariable("applicationId") String applicationId) {
        Bureau response = bureauServices.getBureauByApplicationId(applicationId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Bureau by application fetched", response));
    }

    @GetMapping("/by-bureau/{bureauId}")
    public ResponseEntity<ApiResponse<?>> getBureauById(@PathVariable("bureauId") String bureauId) {
        Bureau response = bureauServices.getBureau(bureauId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Bureau by id fetched", response));
    }
}
