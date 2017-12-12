package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Rostyk on 12.06.2017.
 */
public class PasswordGenerator {

    private static final Logger log = LogManager.getLogger(PasswordGenerator.class);

    public static String hashPassword(String password) {
        log.info("hashPassword(password.length()=" + password.length() + ")");

        if (password == null || password.isEmpty()) {
            log.error("Error : password is null");

            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        log.info("hashPassword() returns : hashedPassword=" + hashedPassword);
        return hashedPassword;
    }
}
