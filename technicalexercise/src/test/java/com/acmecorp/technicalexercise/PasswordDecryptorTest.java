package com.acmecorp.technicalexercise;

import junit.framework.TestCase;

/**
 * Created by mark on 10/22/15.
 */
public class PasswordDecryptorTest extends TestCase {
    /**
     * Verify that decryption matches a known plaintext string
     * @throws Exception
     */
    public void testDecryptMessage() throws Exception {
        final String ENCRYPTED_MESSAGE = "XC4t-9o918jq0P5SkxULT5km0A11qzMQ3TpsiA-tBC4JEq234WYfLXUea-KOMYob5HdvYM6_hQ==";
        final String PLAINTEXT_MESSAGE = "This is a really cool encrypted message";

        PasswordDecryptor decryptor = new PasswordDecryptor("password");
        String decryptedText = decryptor.decryptMessage(ENCRYPTED_MESSAGE);
        assertEquals(PLAINTEXT_MESSAGE, decryptedText);
    }

    /**
     * Verify that encryption with a given password matches decryption with the same password
     */
    public void testEncryptAndDecrypt() {
        final String PLAINTEXT_MESSAGE = "This is another cool message";

        PasswordEncryptor encryptor = new PasswordEncryptor("password");
        String encryptedMessage = encryptor.encryptMessage(PLAINTEXT_MESSAGE);

        PasswordDecryptor decryptor = new PasswordDecryptor("password");
        String decryptedText = decryptor.decryptMessage(encryptedMessage);

        assertEquals(PLAINTEXT_MESSAGE, decryptedText);
    }
}