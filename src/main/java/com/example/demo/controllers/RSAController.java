package com.example.demo.controllers;

import com.example.demo.models.CryptographyDTO;
import com.example.demo.services.RSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api")
public class RSAController {
    @Autowired
    private final RSAService rsaService;

    public RSAController(RSAService rsaService) {
        this.rsaService = rsaService;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody CryptographyDTO requestBody) {
        String ciphertext = rsaService.encrypt(requestBody.getMessage());
        return ResponseEntity.ok(ciphertext);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody CryptographyDTO requestBody) {
        var encryptedMessage = requestBody.getEncryptedMessage();
        try {
            String plaintext = rsaService.decrypt(encryptedMessage);
            return ResponseEntity.ok(plaintext);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Base64 string: " + e.getMessage());
        }
    }
}
