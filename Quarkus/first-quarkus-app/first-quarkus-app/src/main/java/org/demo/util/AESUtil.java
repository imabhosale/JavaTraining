package org.demo.util;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@ApplicationScoped
@Startup
public class AESUtil {

    @ConfigProperty(name = "encryption.secret-key")
    String secretKey;

    @ConfigProperty(name = "encryption.algorithm")
    String algorithm;

    private static String staticKey;
    private static String staticAlgorithm;

    @PostConstruct
    void init() {
        staticKey = secretKey;
        staticAlgorithm = algorithm;
    }

    public static String encrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(staticKey.getBytes(), staticAlgorithm);
            Cipher cipher = Cipher.getInstance(staticAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(staticKey.getBytes(), staticAlgorithm);
            Cipher cipher = Cipher.getInstance(staticAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedValue)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
