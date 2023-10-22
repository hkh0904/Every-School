package com.everyschool.userservice.api.controller.user.request;

import com.everyschool.userservice.api.client.mail.dto.EmailMessage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthEmailRequest {

    @NotBlank
    @Size(max = 100)
    private String email;

    @Builder
    private AuthEmailRequest(String email) {
        this.email = email;
    }

    public EmailMessage toMessage() {
        return EmailMessage.builder()
            .to(this.email)
            .subject("[everySCHOOL] 이메일 인증을 위한 인증 코드 발송")
            .build();
    }
}
