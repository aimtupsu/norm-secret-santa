package com.aimtupsu.normsecretsanta.mail;

import com.aimtupsu.normsecretsanta.config.MailClientConfig;
import com.aimtupsu.normsecretsanta.exception.SecretSantaRuntimeException;
import lombok.RequiredArgsConstructor;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@RequiredArgsConstructor
public class SimpleMailClient implements MailClient {

    private final Session session;
    private final MailClientConfig config;

    @Override
    public void sendHtmlMail(String to, String html) {
        try {
            final MimeMessage message = new MimeMessage(session);
            message.setFrom(config.getFromEmail());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Shhhh..........It's Secret Santa!");
            final MimeMultipart multipart = new MimeMultipart();
            final BodyPart htmlBody = new MimeBodyPart();
            final BodyPart imageBody = new MimeBodyPart();
            htmlBody.setContent(html, "text/html; charset=utf-8");
            final DataSource fds = new FileDataSource(config.getEmailImagePath());
            imageBody.setDataHandler(new DataHandler(fds));
            imageBody.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(htmlBody);
            multipart.addBodyPart(imageBody);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new SecretSantaRuntimeException("Failed to create or/and send new message to: " + to, e);
        }
    }
}
