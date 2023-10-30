package com.everyschool.chatservice.api.service.filterword.dto;

import com.everyschool.chatservice.domain.filterword.FilterWord;
import com.everyschool.chatservice.domain.filterword.MongoSeq;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateFilterWordDto {

    private String loginUserToken;
    private String word;

    @Builder
    private CreateFilterWordDto(String loginUserToken, String word) {
        this.loginUserToken = loginUserToken;
        this.word = word;
    }

    public FilterWord toEntity() {
        return FilterWord.builder()
                .id(MongoSeq.getSeq())
                .word(this.word)
                .build();
    }

}
