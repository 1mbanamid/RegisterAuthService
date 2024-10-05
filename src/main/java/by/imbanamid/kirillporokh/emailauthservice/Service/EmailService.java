package by.imbanamid.kirillporokh.emailauthservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Service class for sending emails.
 * This service uses Spring's JavaMailSender to send emails with HTML content
 * using Thymeleaf as a template engine.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * Sends an email with a verification token to the specified recipient.
     *
     * @param to the email address of the recipient
     * @param token the email verification token to include in the email content
     * @throws RuntimeException if there is an error in sending the email
     */
    public void sendEmail(String to, String token) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject("Email verification");

            Context context = new Context();
            context.setVariable("token", token);

            // Process the HTML template with the token variable
            String htmlContent = templateEngine.process("verification-email", context);

            // Set the email content as HTML
            helper.setText(htmlContent, true);

            // Send the email
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
