package com.aimtupsu.normsecretsanta.mail;

public interface MailClient {

    void sendHtmlMail(String to, String text);

}
