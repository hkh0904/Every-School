package com.everyschool.userservice.api.client.mail;

import com.everyschool.userservice.api.client.mail.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSendClient {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendEmail(EmailMessage emailMessage, String type) {
        String authNumber = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(setContext(authNumber, type),  true);
            javaMailSender.send(mimeMessage);

            log.info("send message success");

            return authNumber;
        } catch (MessagingException e) {
            log.info("fail message success");
            throw new RuntimeException(e);
        }
    }

    private String createCode() {
        Random random = new Random();

        return IntStream.range(0, 6)
            .map(i -> random.nextInt(10))
            .mapToObj(index -> String.valueOf('0' + index))
            .collect(Collectors.joining());
    }

    private String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

}
