package services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Rostyk on 12.06.2017.
 */
public class PasswordGenerator {

    public static String hashPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
