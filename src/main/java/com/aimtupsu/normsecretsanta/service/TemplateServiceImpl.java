package com.aimtupsu.normsecretsanta.service;

import com.aimtupsu.normsecretsanta.config.SecretSantaConfig;
import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final SecretSantaConfig secretSantaConfig;

    public String getFilledEmailMessage(final SecretSantaParticipant giving,
                                        final SecretSantaParticipant receiving) {
        final TemplateEngine templateEngine = new TemplateEngine();
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(resolver);
        Context ct = new Context();
        ct.setVariable("givingName", giving.name());
        ct.setVariable("partyDate", secretSantaConfig.getDate().toString());
        ct.setVariable("partyPlace", secretSantaConfig.getPlace());
        ct.setVariable("receivingName", receiving.name());
        return templateEngine.process("secret-santa-invite.html", ct);
    }

}
