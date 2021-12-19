package com.aimtupsu.normsecretsanta;

import com.aimtupsu.normsecretsanta.config.ConfigLoadFactory;
import com.aimtupsu.normsecretsanta.config.MailClientConfig;
import com.aimtupsu.normsecretsanta.config.SecretSantaConfig;
import com.aimtupsu.normsecretsanta.mail.MailClient;
import com.aimtupsu.normsecretsanta.mail.MailClientFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static final String EMAIL_TO = "example@mail.com";

    public static void main(String[] args) {
        // Load config
        SecretSantaConfig secretSantaConfig = ConfigLoadFactory.INSTANCE.loadSecretSantaConfig();
        log.info("Loaded secret-santa config: {}", secretSantaConfig);
        MailClientConfig mailClientConfig = ConfigLoadFactory.INSTANCE.loadMailClientConfig();
        log.info("Loaded mail-client config: {}", mailClientConfig);

        // Create Mail Client
        final MailClient mailClient = MailClientFactory.INSTANCE.getMailClient(mailClientConfig);

        // Sending message
        final String text = "Test Message. Loaded configs: " + secretSantaConfig + ", " + mailClientConfig;
        mailClient.sendMail(EMAIL_TO, text);
        log.info("Message with text: {} successfully sent to: {}", text, EMAIL_TO);
    }
}
