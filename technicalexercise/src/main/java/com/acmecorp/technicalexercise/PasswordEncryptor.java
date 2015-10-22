package com.acmecorp.technicalexercise;

import com.acmecorp.technicalexercise.utils.PBKDF2;

import org.abstractj.kalium.crypto.Box;
import org.abstractj.kalium.crypto.SecretBox;
import org.abstractj.kalium.encoders.Hex;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by mark on 10/22/15.
 */
public class PasswordEncryptor {
    private final String KNOWN_NONCE = "c0cf6b267bfe6f90b94bc870226d3adaf0a93a403c0db549";

    private String key;

    public PasswordEncryptor(String password) {
        // Generate a symmetric key from the password
        try {
            this.key = PBKDF2.generateKey(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public String encryptMessage(String plaintextMessage) {
        /* Encryption */
        SecretBox box = new SecretBox(Hex.HEX.decode(this.key));
        byte[] encryptedBytes = box.encrypt(Hex.HEX.decode(KNOWN_NONCE), plaintextMessage.getBytes());

        /* Encoding */
        // Encode the message in base64
        byte[] encodedBytes = Base64.getEncoder().encode(encryptedBytes);
        String encodedMessage = new String(encodedBytes);

        // Make the base64 encoding suitable for passing as a URL parameter
        encodedMessage = encodedMessage.replace("+", "-");
        encodedMessage = encodedMessage.replace("/", "_");
        return encodedMessage;
    }
}
