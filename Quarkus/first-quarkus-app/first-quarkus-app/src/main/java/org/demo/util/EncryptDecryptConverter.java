package org.demo.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptDecryptConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        String encrypted = AESUtil.encrypt(attribute);
        System.out.println("Encrypting value before saving to DB: " + attribute + " → " + encrypted);
        return encrypted;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String decrypted = AESUtil.decrypt(dbData);
        System.out.println("🔓 Decrypting value after fetching from DB: " + dbData + " → " + decrypted);

        return decrypted;
    }
}
