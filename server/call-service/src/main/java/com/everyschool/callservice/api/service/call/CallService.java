package com.everyschool.callservice.api.service.call;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.dto.CreateCallDto;
import com.everyschool.callservice.domain.call.Call;
import com.everyschool.callservice.domain.call.repository.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CallService {

    private final CallRepository callRepository;
    private final UserServiceClient userServiceClient;

    public CallResponse createCallInfo(CreateCallDto dto, String otherUserKey, String token) {
        // memo: 선생님이 전화를 끊을때만 저장 -> token 선생일수밖에 없음
        // TODO: 로그인 사용자의 토큰으로 user-service에 pk 요청
        //       otherUserKey를 user-service에 pk 요청

        UserInfo otherUser = userServiceClient.searchUserInfoByUserKey(otherUserKey);
        UserInfo teacher = userServiceClient.searchUserInfo(token);

        Call savedCall = insertCall(dto, teacher.getUserId(), otherUser.getUserId());

        CallResponse response = CallResponse.of(savedCall);
        response.setTeacherName(teacher.getUserName());
        response.setOtherUserName(otherUser.getUserName());

        return response;
    }

    private Call insertCall(CreateCallDto dto, Long teacherId, Long otherUserId) {
        Call call = Call.builder()
                .teacherId(teacherId)
                .otherUserId(otherUserId)
                .sender(dto.getSender())
                .startDateTime(dto.getStartDateTime())
                .endDateTime(dto.getEndDateTime())
                .uploadFileName(dto.getUploadFileName())
                .storeFileName(dto.getStoreFileName())
                .isBad(dto.getIsBad())
                .build();
        return callRepository.save(call);
    }

}
