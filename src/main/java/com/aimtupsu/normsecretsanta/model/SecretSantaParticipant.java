package com.aimtupsu.normsecretsanta.model;

import java.util.regex.Pattern;

/**
 * Info about Secret Santa participant.
 * Any participant has a name and email.
 */
public record SecretSantaParticipant(String name, String email) {

    /**
     * Simple regex for Email validation.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\S+@\\S+\\.\\S+");

    /**
     * Constructor with simple Email validation.
     */
    public SecretSantaParticipant {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("""
                Email is not in the correct format:
                """ + email + """
                Please fix it in config file.
                """
            );
        }
    }
}
