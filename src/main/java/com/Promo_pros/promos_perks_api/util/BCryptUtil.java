package com.Promo_pros.promos_perks_api.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtil {
    //Method to hash password

    public static String generatedSecurePassword(String originalPassword){
        return BCrypt.hashpw(originalPassword, BCrypt.gensalt());
    }

    public static boolean matchPasswords(String originalPassword, String generatedSecurePassword){
        return BCrypt.checkpw(originalPassword, generatedSecurePassword);
    }
}
