package com.workup.workup.services.Email;


import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface EmailService {
    void sendSimpleMessage(
            String to,
            String subject,
            String body
    );

    void sendHtmlMessage(
            String to,
            String subject,
            String htmlBody
    ) throws MessagingException;

    void sendSimpleMessageUsingTemplate(
            String to,
            String subject,
            String ...templateModel
    );

    void sendMessageWithAttachment(
            String to,
            String subject,
            String body,
            String pathToAttachment
    );

    void sendUserMessageUsingThymeleafTemplate(
            String to,
            String subject,
            HashMap<String, Object> templateModel
    ) throws IOException, MessagingException;

    void sendProjectMessageUsingThymeleafTemplate(
            String to,
            String subject,
            HashMap<String, Object> templateModel
    ) throws IOException, MessagingException;
}
