package com.everyschool.callservice.api.service.donotdisturb;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.api.service.donotdisturb.dto.DoNotDisturbDto;
import com.everyschool.callservice.domain.donotdisturb.DoNotDisturb;
import com.everyschool.callservice.domain.donotdisturb.repository.DoNotDisturbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DoNotDisturbService {

    private final UserServiceClient userServiceClient;
    private final DoNotDisturbRepository doNotDisturbRepository;

    public DoNotDisturbResponse createDoNotDisturb(DoNotDisturbDto dto, String token) {
        log.debug("call DoNotDisturbService#createDoNotDisturb");

        UserInfo user = userServiceClient.searchUserInfo(token);
        log.debug("user = {}", user);
        dto.setTeacherId(user.getUserId());

        DoNotDisturb doNotDisturb = insertDoNotDisturb(dto);
        log.debug("doNotDisturb = {}", doNotDisturb);

        return DoNotDisturbResponse.of(doNotDisturb);
    }

    public DoNotDisturbResponse updateIsActivate(Long doNotDisturbId) {
        log.debug("call DoNotDisturbService#updateIsActivate");
        Optional<DoNotDisturb> doNotDisturb = doNotDisturbRepository.findById(doNotDisturbId);

        if (doNotDisturb.isEmpty()) {
            throw new NoSuchElementException("해당 아이디는 존재 하지 않습니다.");
        }

        log.debug("before doNotDisturb = {}", doNotDisturb.get());
        doNotDisturb.get().updateIsActivate();
        log.debug("after doNotDisturb = {}", doNotDisturb.get());

        DoNotDisturb result = doNotDisturbRepository.save(doNotDisturb.get());
        return DoNotDisturbResponse.of(result);
    }

    private DoNotDisturb insertDoNotDisturb(DoNotDisturbDto dto) {
        DoNotDisturb doNotDisturb = DoNotDisturb.builder()
                .teacherId(dto.getTeacherId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .isActivate(dto.getIsActivate())
                .build();

        return doNotDisturbRepository.save(doNotDisturb);
    }

}
