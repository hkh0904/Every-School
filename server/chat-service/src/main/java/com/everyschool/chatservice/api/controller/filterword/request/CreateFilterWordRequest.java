package com.everyschool.chatservice.api.controller.filterword.request;

import com.everyschool.chatservice.api.service.filterword.dto.CreateFilterWordDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateFilterWordRequest {

    @NotBlank
    private String word;

    @Builder
    private CreateFilterWordRequest(String word) {
        this.word = word;
    }

    public CreateFilterWordDto toDto(String token) {
        return CreateFilterWordDto.builder()
                .loginUserToken(token)
                .word(this.word)
                .build();
    }
}
