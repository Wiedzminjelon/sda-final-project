package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.NotificationEmail;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder contentBuilder;

    @Async
    void sendMail (NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("twitterapp@twitterapp.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(contentBuilder.build(notificationEmail.getBody()));
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Activation mail sent!");
        }catch (MailException exception){
            throw new SpringTwitterException("Exception occured when sending mail!");
        }
    }
}


