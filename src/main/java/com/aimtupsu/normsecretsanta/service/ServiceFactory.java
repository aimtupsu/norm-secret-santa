package com.aimtupsu.normsecretsanta.service;

import com.aimtupsu.normsecretsanta.config.ConfigLoadFactory;
import com.aimtupsu.normsecretsanta.config.SecretSantaConfig;
import com.aimtupsu.normsecretsanta.mail.MailClient;
import com.aimtupsu.normsecretsanta.mail.MailClientFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceFactory {

    public static final ServiceFactory INSTANCE = new ServiceFactory();

    private SecretSantaService secretSantaService;

    public SecretSantaService getSecretSantaService() {
        if (secretSantaService == null) {
            secretSantaService = createSecretSantaService();
        }
        return secretSantaService;
    }

    private SecretSantaService createSecretSantaService() {
        final SecretSantaConfig secretSantaConfig = ConfigLoadFactory.INSTANCE.loadSecretSantaConfig();
        final MailClient mailClient = MailClientFactory.INSTANCE.getMailClient();
        final TemplateService templateService = createTemplateService();
        return new SecretSantaServiceImpl(secretSantaConfig, mailClient, templateService);
    }

    private TemplateService createTemplateService() {
        final SecretSantaConfig secretSantaConfig = ConfigLoadFactory.INSTANCE.loadSecretSantaConfig();
        return new TemplateServiceImpl(secretSantaConfig);
    }

}
