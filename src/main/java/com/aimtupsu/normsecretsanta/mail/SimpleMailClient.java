package com.aimtupsu.normsecretsanta.mail;

import com.aimtupsu.normsecretsanta.config.MailClientConfig;
import com.aimtupsu.normsecretsanta.exception.SecretSantaRuntimeException;
import lombok.RequiredArgsConstructor;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
public class SimpleMailClient implements MailClient {

    private final Session session;
    private final MailClientConfig config;

    @Override
    public void sendMail(String to, String text) {
        try {
            final MimeMessage message = new MimeMessage(session);
            message.setFrom(config.getFromEmail());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Shhhh..........It's Secret Santa!");
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new SecretSantaRuntimeException(
                    "Failed to create or/and send new message to: " + to
                    + " with text: " + text,
                    e
            );
        }
    }
}
