package track.messenger.security;

import org.apache.commons.lang.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by geoolekom on 19.11.16.
 */
public class CryptoSystem {

    private MessageDigest hasher;
    private String hashAlgorithm;
    private Integer saltLength;
    private Integer numberOfIterations;

    public CryptoSystem() {}

    public CryptoSystem(String hashAlgorithm) throws InstantiationException {
        setHashAlgorithm(hashAlgorithm);
    }

    public void setHashAlgorithm(String hashAlgorithm) throws InstantiationException {
        this.hashAlgorithm = hashAlgorithm;
        try {
            hasher = MessageDigest.getInstance(hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new InstantiationException("Нет такого метода шифрования. ");
        }
    }

    public void setSaltLength(Integer saltLength) {
        this.saltLength = saltLength;
    }

    public void setNumberOfIterations(Integer numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public Integer getSaltLength() {
        return saltLength;
    }

    public String encrypt(String value, String salt) {

        if (value == null || salt == null) {
            return null;
        }
        for (int count = 0; count < numberOfIterations; count ++ ) {
            hasher.update((value + salt).getBytes());
            value = new String(hasher.digest());
            hasher.reset();
        }
        return value;
    }

    public boolean check(String encrypted, String raw, String salt) {
        if (encrypted == null) {
            return false;
        } else {
            return (encrypted.compareTo(encrypt(raw, salt)) == 0);
        }
    }


}
