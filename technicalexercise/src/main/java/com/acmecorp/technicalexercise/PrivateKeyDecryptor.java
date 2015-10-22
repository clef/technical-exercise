package com.acmecorp.technicalexercise;

import org.abstractj.kalium.crypto.Box;
import org.abstractj.kalium.crypto.SecretBox;
import org.abstractj.kalium.encoders.Hex;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;

import java.util.Base64;

/**
 * Created by mark on 10/22/15.
 */
public class PrivateKeyDecryptor {
    // This hardcoded public key matches the hardcoded private key
    // in PublicKeyEncryptor (it verifies the digital signature)
    private final PublicKey SENDER_PUBLIC_KEY = new PublicKey("dec6e11f0e7832d1c5b62535a25d9e89a383145af957a6a9c648a47276e0af08");
    private final String KNOWN_NONCE = "c0cf6b267bfe6f90b94bc870226d3adaf0a93a403c0db549";

    private PrivateKey privateKey;

    public PrivateKeyDecryptor(String privateKeyString) {
        // This private key is used to decrypt the message
        this.privateKey = new PrivateKey(privateKeyString);
    }

    public String decrypt(String encodedMessage) {
        // Undo making the base64 encoding URL safe
        String encryptedMessage = encodedMessage.replace("-", "+");
        encryptedMessage = encryptedMessage.replace("_", "/");

        // Decode the message from base64
        byte[] encodedBytes = Base64.getDecoder().decode(encodedMessage.getBytes());

        // Decrypt the message with my private key
        Box box = new Box(SENDER_PUBLIC_KEY, this.privateKey);
        byte[] decryptedMessage = box.decrypt(Hex.HEX.decode(KNOWN_NONCE), encodedBytes);
        return new String(decryptedMessage);
    }
}
