package socialmediaapp.twitterinspiredapp.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.NotificationEmail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Data
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(NotificationEmail notificationEmail, boolean isHtmlContent) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(notificationEmail.getRecipient());
        mimeMessageHelper.setSubject(notificationEmail.getSubject());
        mimeMessageHelper.setText(notificationEmail.getBody(), isHtmlContent);
        mimeMessageHelper.setFrom("twitterapp@twitterapp.com");

        try {
            mailSender.send(mimeMessage);
            log.info("Activation Email sent!");
        } catch (MailException exception) {
            throw new SpringTwitterException("Exception occurred when sending an email!");
        }
    }
}


