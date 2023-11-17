package com.everyschool.boardservice.api.app.service.scrap;

import com.everyschool.boardservice.api.SliceResponse;
import com.everyschool.boardservice.api.app.controller.scrap.response.MyScrapResponse;
import com.everyschool.boardservice.api.client.UserServiceClient;
import com.everyschool.boardservice.api.client.response.UserInfo;
import com.everyschool.boardservice.domain.board.repository.ScrapQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ScrapAppQueryService {

    private final ScrapQueryRepository scrapQueryRepository;
    private final UserServiceClient userServiceClient;

    public SliceResponse<MyScrapResponse> searchMyScrap(String userKey, Pageable pageable) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Slice<MyScrapResponse> content = scrapQueryRepository.findByUserId(userInfo.getUserId(), pageable);

        return new SliceResponse<>(content);
    }
}
