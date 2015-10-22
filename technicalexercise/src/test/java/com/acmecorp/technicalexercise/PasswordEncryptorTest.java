package com.acmecorp.technicalexercise;

import junit.framework.TestCase;

/**
 * Created by mark on 10/22/15.
 */
public class PasswordEncryptorTest extends TestCase {
    /**
     * Verify that encryption matches a known encrypted string
     * @throws Exception
     */
    public void testEncryptMessage() throws Exception {
        String PLAINTEXT_MESSAGE = "This is a really cool encrypted message";
        String ENCRYPTED_MESSAGE = "XC4t-9o918jq0P5SkxULT5km0A11qzMQ3TpsiA-tBC4JEq234WYfLXUea-KOMYob5HdvYM6_hQ==";

        PasswordEncryptor encryptor = new PasswordEncryptor("password");
        String encryptedMessage = encryptor.encryptMessage(PLAINTEXT_MESSAGE);
        assertEquals(ENCRYPTED_MESSAGE, encryptedMessage);
    }
}