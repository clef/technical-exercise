package com.acmecorp.technicalexercise;

import org.abstractj.kalium.crypto.Box;
import org.abstractj.kalium.encoders.Hex;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;

import java.util.Base64;

/**
 * Created by mark on 10/22/15.
 */
public class PublicKeyEncryptor {
    // This hardcoded private key is used so that the recipient of the messages *knows*
    // we sent it (it is used to create a digital signature)
    private final PrivateKey MY_PRIVATE_KEY = new PrivateKey("9de45a6041cd2ba834e6920ebe4178c02f34c7503e93ee859d66686bbed4966a");
    private final String KNOWN_NONCE = "c0cf6b267bfe6f90b94bc870226d3adaf0a93a403c0db549";

    private PublicKey recipientPublicKey;

    public PublicKeyEncryptor(String recipientPublicKeyString) {
        // This public key is used to encrypt the message
        this.recipientPublicKey = new PublicKey(recipientPublicKeyString);
    }

    public String encrypt(String plaintextMessage) {
        /* Encryption */
        Box box = new Box(recipientPublicKey, MY_PRIVATE_KEY);
        byte[] encryptedBytes = box.encrypt(Hex.HEX.decode(KNOWN_NONCE), plaintextMessage.getBytes());

        /* Encoding */
        // Encode the message in base64
        byte[] encodedBytes = Base64.getEncoder().encode(encryptedBytes);
        String encodedMessage = new String(encodedBytes);
        return encodedMessage;
    }
}
