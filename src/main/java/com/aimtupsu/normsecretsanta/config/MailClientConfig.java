package com.aimtupsu.normsecretsanta.config;

import java.util.Properties;

public interface MailClientConfig {

    String getLogin();

    String getFromEmail();

    String getPass();

    boolean isDebugActive();

    String getEmailImagePath();

    Properties getProperties();
}
