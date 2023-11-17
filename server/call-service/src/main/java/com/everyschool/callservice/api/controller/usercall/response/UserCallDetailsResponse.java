package com.everyschool.callservice.api.controller.usercall.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCallDetailsResponse {

    private String fileName;
    private String content;
    private Integer start;
    private Integer length;
    private String sentiment;
    private Float neutral;
    private Float positive;
    private Float negative;

    @Builder
    public UserCallDetailsResponse(String fileName, String content, Integer start, Integer length, String sentiment, Float neutral,
                                   Float positive, Float negative) {
        this.fileName = fileName;
        this.content = content;
        this.start = start;
        this.length = length;
        this.sentiment = sentiment;
        this.neutral = neutral;
        this.positive = positive;
        this.negative = negative;
    }
}
