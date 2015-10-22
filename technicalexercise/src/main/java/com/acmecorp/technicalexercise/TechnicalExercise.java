package com.acmecorp.technicalexercise;

public class TechnicalExercise {
    public static void main(String[] args) {
        PasswordEncryptor encryptor = new PasswordEncryptor("password");
        String encryptedMessage = encryptor.encryptMessage("This is a really cool encrypted message");
        System.out.println(encryptedMessage);

        PasswordDecryptor decryptor = new PasswordDecryptor("password");
        System.out.println(decryptor.decryptMessage(encryptedMessage));

        PublicKeyEncryptor encryptor1 = new PublicKeyEncryptor("6ccb208e0bb4d875580ea004884a49e813455d22d7415ff71d6521335458780a");
        String encryptedMsg = encryptor1.encrypt("This is a secret message ~ for your eyes only");
        System.out.println(encryptedMsg);

        PrivateKeyDecryptor decryptor1 = new PrivateKeyDecryptor("9e6ece687d02d8ae5185f75b8f3a941abc67aa8b40a65954941ad425dbf7cb64");
        System.out.println(decryptor1.decrypt(encryptedMsg));
    }
}
