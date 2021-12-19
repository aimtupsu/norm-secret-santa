package com.aimtupsu.normsecretsanta.mail;

import com.aimtupsu.normsecretsanta.config.MailClientConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailClientFactory {

    public static final MailClientFactory INSTANCE = new MailClientFactory();

    private MailClient mailClient;

    public MailClient getMailClient(final MailClientConfig config) {
        if (this.mailClient == null) {
            this.mailClient = createMailClient(config);
        }
        return this.mailClient;
    }

    private MailClient createMailClient(final MailClientConfig config) {
        final Session session = createSession(config);
        return new SimpleMailClient(session, config);
    }

    private Session createSession(final MailClientConfig config) {
        config.getProperties();
        final Session session = Session.getInstance(config.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getLogin(), config.getPass());
            }
        });
        session.setDebug(config.isDebugActive());
        return session;
    }

}
