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
        // TODO: 선생님이 전화를 끊을때만 저장 -> token 선생일수밖에 없음

        UserInfo otherUser = userServiceClient.searchUserInfoByUserKey(otherUserKey);
        UserInfo teacher = userServiceClient.searchUserInfo(token);

        String senderName = otherUser.getUserName();
        String receiverName = teacher.getUserName();

        if (dto.getSender().equals("T")) {
            receiverName = otherUser.getUserName();
            senderName = teacher.getUserName();
        }

        Call savedCall = insertCall(dto, teacher.getUserId(), otherUser.getUserId(), senderName, receiverName);
        return CallResponse.of(savedCall);
    }

    private Call insertCall(CreateCallDto dto, Long teacherId, Long otherUserId, String senderName, String receiverName) {
        Call call = Call.builder()
                .teacherId(teacherId)
                .otherUserId(otherUserId)
                .sender(dto.getSender())
                .senderName(senderName)
                .receiverName(receiverName)
                .startDateTime(dto.getStartDateTime())
                .endDateTime(dto.getEndDateTime())
                .uploadFileName(dto.getUploadFileName())
                .storeFileName(dto.getStoreFileName())
                .isBad(dto.getIsBad())
                .build();

        return callRepository.save(call);
    }

}
