package com.acmecorp.technicalexercise;

import junit.framework.TestCase;

/**
 * Created by mark on 10/22/15.
 */
public class PublicKeyEncryptorTest extends TestCase {

    /**
     * Verify that encryption matches a known encrypted string
     */
    public void testEncrypt() throws Exception {
        final String ENCRYPTED_MESSAGE = "90g-pVWMCufEZfRsrr9yub83Mxx-lO8SkyuX3F5gtqn9DWonypSNHNXyBl9_j2hDci1SRpE4jm4KLX_LLw==";

        PublicKeyEncryptor encryptor = new PublicKeyEncryptor("6ccb208e0bb4d875580ea004884a49e813455d22d7415ff71d6521335458780a");
        String encryptedMessage = encryptor.encrypt("This is a secret message ~ for your eyes only");
        assertEquals(ENCRYPTED_MESSAGE, encryptedMessage);
    }
}