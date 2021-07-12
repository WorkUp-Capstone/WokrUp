package com.workup.workup.services.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service("EmailService")
public class EmailServiceImplementation implements EmailService{

    private static final String defaultEmail = "admin@example.com";

    @Autowired
    private JavaMailSender emailSender;

    private SimpleMailMessage template;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("classpath:/temporary.png")
    private Resource resourceFile;

    @Override
    public void sendSimpleMessage(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(defaultEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(defaultEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("attachment.png", (org.springframework.core.io.Resource) resourceFile);
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel) {
        String text = String.format(template.getText(), templateModel);
        sendSimpleMessage(to,subject,text);
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(defaultEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, HashMap<String, Object> templateModel) throws IOException, MessagingException {
            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("mail-contact-user.html", thymeleafContext);

            sendHtmlMessage(to, subject, htmlBody);
        }
    }

