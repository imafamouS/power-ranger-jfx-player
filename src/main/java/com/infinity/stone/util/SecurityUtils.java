package com.infinity.stone.util;

import java.security.Key;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by infamouSs on 4/24/18.
 */

public class SecurityUtils {
    
    private static final String ALGORITHM = "AES";
    private static final String AES_KEY;
    private static final byte[] KEY_AES;
    
    
    static {
        AES_KEY = "12345678";
        KEY_AES = AES_KEY.getBytes();
    }
    
    
    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    private static Key generateKey() throws Exception {
        return new SecretKeySpec(KEY_AES, ALGORITHM);
    }
    
    public static String encrypt(String valueToEnc) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            
            return Base64.getEncoder().encodeToString(encValue);
        } catch (Exception e) {
            throw new UnknownError();
        }
        
    }
    
    public static String decrypt(String encryptedValue) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
            byte[] decValue = c.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
            throw new UnknownError();
        }
        
    }
}
