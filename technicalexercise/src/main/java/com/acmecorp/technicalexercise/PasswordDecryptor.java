package com.acmecorp.technicalexercise;

import com.acmecorp.technicalexercise.utils.PBKDF2;

import org.abstractj.kalium.crypto.SecretBox;
import org.abstractj.kalium.encoders.Hex;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by mark on 10/22/15.
 */
public class PasswordDecryptor {
    private final String KNOWN_NONCE = "c0cf6b267bfe6f90b94bc870226d3adaf0a93a403c0db549";
    private String key;

    public PasswordDecryptor(String password) {
        try {
            this.key = PBKDF2.generateKey(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public String decryptMessage(String encodedMessage) {
        // Undo making the base64 encoding URL safe
        String encryptedMessage = encodedMessage.replace("-", "+");
        encryptedMessage = encryptedMessage.replace("_", "/");

        // Decode the message from base64
        byte[] encodedBytes = Base64.getDecoder().decode(encryptedMessage.getBytes());

        // Decrypt the message
        SecretBox box = new SecretBox(Hex.HEX.decode(this.key));
        byte[] decryptedMessage = box.decrypt(Hex.HEX.decode(KNOWN_NONCE), encodedBytes);
        return new String(decryptedMessage);
    }
}
