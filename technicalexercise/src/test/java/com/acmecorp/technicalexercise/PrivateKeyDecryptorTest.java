package com.acmecorp.technicalexercise;

import junit.framework.TestCase;

import java.security.PublicKey;

/**
 * Created by mark on 10/22/15.
 */
public class PrivateKeyDecryptorTest extends TestCase {
    /**
     * Verify that decryption matches a known plaintext string
     * @throws Exception
     */
    public void testDecrypt() throws Exception {
        final String ENCRYPTED_MESSAGE = "90g-pVWMCufEZfRsrr9yub83Mxx-lO8SkyuX3F5gtqn9DWonypSNHNXyBl9_j2hDci1SRpE4jm4KLX_LLw==";
        final String PLAINTEXT_MESSAGE = "This is a secret message ~ for your eyes only";

        PrivateKeyDecryptor decryptor = new PrivateKeyDecryptor("9e6ece687d02d8ae5185f75b8f3a941abc67aa8b40a65954941ad425dbf7cb64");
        String decryptedMessage = decryptor.decrypt(ENCRYPTED_MESSAGE);

        assertEquals(PLAINTEXT_MESSAGE, decryptedMessage);
    }

    /**
     * Verify that encryption with a given public key matches decryption with the corresponding private key
     */
    public void testEncryptAndDecrypt() {
        final String PLAINTEXT_MESSAGE = "This is another cool message";

        PublicKeyEncryptor encryptor = new PublicKeyEncryptor("6ccb208e0bb4d875580ea004884a49e813455d22d7415ff71d6521335458780a");
        String encryptedMessage = encryptor.encrypt(PLAINTEXT_MESSAGE);

        PrivateKeyDecryptor decryptor = new PrivateKeyDecryptor("9e6ece687d02d8ae5185f75b8f3a941abc67aa8b40a65954941ad425dbf7cb64");
        String decryptedText = decryptor.decrypt(encryptedMessage);

        assertEquals(PLAINTEXT_MESSAGE, decryptedText);
    }
}