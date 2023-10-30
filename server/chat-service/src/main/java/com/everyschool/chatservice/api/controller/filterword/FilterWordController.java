package com.everyschool.chatservice.api.controller.filterword;

import com.everyschool.chatservice.api.ApiResponse;
import com.everyschool.chatservice.api.controller.filterword.request.CreateFilterWordRequest;
import com.everyschool.chatservice.api.service.filterword.FilterWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat-service/v1/filters")
public class FilterWordController {

    private final FilterWordService filterWordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createFilterWord(@RequestBody @Valid CreateFilterWordRequest request,
                                              @RequestHeader("Authorization") String token) {

        Long filterWordId = filterWordService.createFilterWord(request.toDto(token));
        return ApiResponse.created(filterWordId);
    }
}
