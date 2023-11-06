package com.everyschool.callservice.api.service.call;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.call.dto.CreateUserCallDto;
import com.everyschool.callservice.domain.call.UserCall;
import com.everyschool.callservice.domain.call.repository.UserCallRepository;
import com.everyschool.callservice.domain.callrecord.UserCallDetails;
import com.everyschool.callservice.domain.callrecord.repository.UserCallRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCallService {

    private final UserCallRepository userCallRepository;
    private final UserCallRecordRepository userCallRecordRepository;
    private final UserServiceClient userServiceClient;

    public UserCallResponse createCallInfo(CreateUserCallDto dto, String otherUserKey, String token) {

        UserInfo otherUser = userServiceClient.searchUserInfoByUserKey(otherUserKey);
        UserInfo teacher = userServiceClient.searchUserInfo(token);

        String senderName = otherUser.getUserName();
        String receiverName = teacher.getUserName();

        if (dto.getSender().equals("T")) {
            receiverName = otherUser.getUserName();
            senderName = teacher.getUserName();
        }

        UserCall savedUserCall = insertCall(dto, teacher.getUserId(), otherUser.getUserId(), senderName, receiverName);
//        UserCall savedUserCall = insertCall(dto, 1L, 2L, "선생님", "학부모");
        return UserCallResponse.of(savedUserCall);
    }

    public void updateCallInfo(Long callId, RecordResultInfo res) {
        // 데이터베이스에서 해당 엔티티를 가져옵니다.
        Optional<UserCall> optionalCall = userCallRepository.findById(callId);

        if (optionalCall.isPresent()) {
            UserCall userCall = optionalCall.get();

            userCall.updateCallInfo(res.getOverallResult(), res.getOverallPercent().get(0), res.getOverallPercent().get(1), res.getOverallPercent().get(2)
                                , res.getOverallResult().equals("negative"));

            UserCall updatedUserCall = userCallRepository.save(userCall);

            System.out.println(updatedUserCall);
            List<UserCallDetails> userCallDetails = new ArrayList<>();
            res.getDetailsResult().forEach(e -> {
                System.out.println(e);
                UserCallDetails callDetails = UserCallDetails.builder()
                        .userCall(userCall)
                        .content(e.getContent())
                        .start(e.getStart())
                        .length(e.getLength())
                        .sentiment(e.getSentiment())
                        .neutral(e.getConfidence().get(0))
                        .positive(e.getConfidence().get(1))
                        .negative(e.getConfidence().get(2))
                        .build();
                userCallDetails.add(callDetails);
            });
            userCallRecordRepository.saveAll(userCallDetails);

        } else {
            throw new EntityNotFoundException("UserCall not found");
        }

    }

    private UserCall insertCall(CreateUserCallDto dto, Long teacherId, Long otherUserId, String senderName, String receiverName) {
        UserCall userCall = UserCall.builder()
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

        return userCallRepository.save(userCall);
    }

}
