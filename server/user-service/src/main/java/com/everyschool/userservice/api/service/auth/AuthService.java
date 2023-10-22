package com.everyschool.userservice.api.service.auth;

import com.everyschool.userservice.api.client.mail.MailSendClient;
import com.everyschool.userservice.api.client.mail.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final MailSendClient mailSendClient;
    private final RedisTemplate<String, String> redisTemplate;

    public void sendEmail(EmailMessage message) {
        String authNumber = mailSendClient.sendEmail(message, "email");

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set(message.getTo(), authNumber, 3, TimeUnit.MINUTES);
    }
}
