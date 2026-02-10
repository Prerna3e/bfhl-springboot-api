package com.example.bfhl.controller;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import com.example.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // Allow all origins for testing/deployment flexibility
public class BfhlController {

    private final BfhlService bfhlService;

    @Value("${chitkara.email}")
    private String officialEmail;

    @Autowired
    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> processRequest(@RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.processRequest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<BfhlResponse> checkHealth() {
        BfhlResponse response = new BfhlResponse();
        response.setSuccess(true);
        response.setOfficialEmail(officialEmail);
        return ResponseEntity.ok(response);
    }
}
