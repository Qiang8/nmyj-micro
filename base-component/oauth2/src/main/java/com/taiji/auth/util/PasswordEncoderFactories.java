package com.taiji.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for creating {@link PasswordEncoder} instances
 * @author Rob Winch
 * @since 5.0
 */
public class PasswordEncoderFactories {

    /**
     * Creates a {@link DelegatingPasswordEncoder} with default mappings. Additional
     * mappings may be added and the encoding will be updated to conform with best
     * practices. However, due to the nature of {@link DelegatingPasswordEncoder} the
     * updates should not impact users. The mappings current are:
     *
     * <ul>
     * <li>bcrypt - {@link BCryptPasswordEncoder} (Also used for encoding)</li>
     * <li>ldap - {@link LdapShaPasswordEncoder}</li>
     * <li>MD4 - {@link Md4PasswordEncoder}</li>
     * <li>MD5 - {@code new MessageDigestPasswordEncoder("MD5")}</li>
     * <li>noop - {@link NoOpPasswordEncoder}</li>
     * <li>pbkdf2 - {@link Pbkdf2PasswordEncoder}</li>
     * <li>scrypt - {@link SCryptPasswordEncoder}</li>
     * <li>SHA-1 - {@code new MessageDigestPasswordEncoder("SHA-1")}</li>
     * <li>SHA-256 - {@code new MessageDigestPasswordEncoder("SHA-256")}</li>
     * <li>sha256 - {@link StandardPasswordEncoder}</li>
     * </ul>
     *
     * @return the {@link PasswordEncoder} to use
     */
    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String                       encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders   = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("taiji", new TaijiPasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    private PasswordEncoderFactories() {}
}
