package mailer;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {


    public static void main(String[] args) {
        // Sender's email address and password
        String senderEmail = "noreply@supportzebra.com";
        String userName = "AKIASKFBBHWC2AFKXROT";
        String senderPassword = "BNcZqYE+8bM6fTMfWwUpkokTz9+VRwcUQ+aqpiBlniNM";
        // Recipient's email address
        String recipientEmail = "gerzon.udang@talentamp.com";

        // Email subject and content
        String subject = "Email Verification";
        String content = "Click the button below to verify your email:\n\n"
                + "<button>Verify Email</button>";

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

            // Create a multipart message to include both text and HTML content
            MimeMultipart multipart = new MimeMultipart("alternative");

            // Create a text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(content, "utf-8");

            // Create an HTML part
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(content, "text/html; charset=utf-8");

            // Add both parts to the multipart message
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);

            // Set the content of the message to the multipart message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }

}
