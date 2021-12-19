package com.aimtupsu.normsecretsanta.config;

import java.util.Properties;
import java.util.regex.Pattern;

record MailClientConfigPojo(String login,
                            String pass,
                            boolean auth,
                            String host,
                            String port,
                            String socketPort,
                            String emailImagePath,
                            boolean debug) implements MailClientConfig {

    /**
     * Simple regex for Email validation.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\S+@\\S+\\.\\S+");

    public MailClientConfigPojo {
        if (!EMAIL_PATTERN.matcher(login).matches()) {
            throw new IllegalArgumentException("""
                Email is not in the correct format:
                """ + login + """
                Please fix it in config file.
                """
            );
        }
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getFromEmail() {
        return login;
    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public boolean isDebugActive() {
        return debug;
    }

    @Override
    public String getEmailImagePath() {
        return emailImagePath;
    }

    @SuppressWarnings("java:S5527")
    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", Boolean.toString(auth));
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }
}
