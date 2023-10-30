package com.everyschool.chatservice.api.service.filterword;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.service.filterword.dto.CreateFilterWordDto;
import com.everyschool.chatservice.domain.filterword.FilterWord;
import com.everyschool.chatservice.domain.filterword.repository.FilterWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FilterWordService {

    private final FilterWordRepository filterWordRepository;

    private final UserServiceClient userServiceClient;

    public Long createFilterWord(CreateFilterWordDto dto) {
        UserInfo loginUser = userServiceClient.searchUserInfo(dto.getLoginUserToken());
        if (loginUser.getUserType() != 'A') {
            throw new IllegalArgumentException("관리자 계정만 접근 가능합니다.");
        }

        FilterWord saved = filterWordRepository.save(dto.toEntity());
        return saved.getId();
    }
}
