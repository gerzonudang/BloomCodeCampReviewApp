package com.hcc.services;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


public class EmailService {

    private String code;

    public EmailService() {
    }

    public EmailService(String code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void send() {
        // Sender's email address and password
        String senderEmail = "noreply@supportzebra.com";
        String userName = "AKIASKFBBHWC2AFKXROT";
        String senderPassword = "BNcZqYE+8bM6fTMfWwUpkokTz9+VRwcUQ+aqpiBlniNM";
        // Recipient's email address
        String recipientEmail = "gerzon.udang@talentamp.com";

        // Email subject and content
        String subject = "Email Verification";
        // Email content with HTML template
        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        .container {\n" +
                "            background-color: #f5f5f5;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #333;\n" +
                "            color: #fff;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "        .content {\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            background-color: #4caf50;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 4px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h2>Email Verification</h2>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Click the button below to verify your email address:</p>\n" +
                "            <a class=\"button\" href=\"http://localhost:8080/api/account/verify/"+ this.code+" \">Verify Email</a>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "email-smtp.us-west-2.amazonaws.com");
        properties.put("mail.smtp.port", "587");

        // Create a session with the mail server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, senderPassword);
            }
        });

        try {
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);

            // Create a MimeMultipart for storing the HTML content and related resources (if any)
            MimeMultipart multipart = new MimeMultipart("related");

            // Create a MimeBodyPart for the HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");

            // Add the HTML part to the multipart
            multipart.addBodyPart(htmlPart);

            // Set the content of the message to the multipart
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }
}
