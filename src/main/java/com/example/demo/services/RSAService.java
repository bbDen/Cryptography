package com.example.demo.services;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class RSAService {
    private KeyPairGenerator keyPairGenerator;
    private Cipher cipher;
    private KeyPair keyPair;

    public RSAService() throws NoSuchAlgorithmException, NoSuchPaddingException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        cipher = Cipher.getInstance("RSA");
        keyPair = keyPairGenerator.generateKeyPair();
    }

    public String encrypt(String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting message", e);
        }
    }

    public String decrypt(String encryptedText) {

        try {
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            var some_value  = cipher.doFinal(decodedBytes);
            return new String(some_value);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting message", e);
        }
    }
}
