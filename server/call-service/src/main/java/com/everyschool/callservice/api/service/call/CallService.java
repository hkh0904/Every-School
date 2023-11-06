package com.everyschool.callservice.api.service.call;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.dto.CreateCallDto;
import com.everyschool.callservice.domain.call.Call;
import com.everyschool.callservice.domain.call.repository.CallRepository;
import com.everyschool.callservice.domain.callrecord.CallRecord;
import com.everyschool.callservice.domain.callrecord.repository.CallRecordRepository;
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
public class CallService {

    private final CallRepository callRepository;
    private final CallRecordRepository callRecordRepository;
    private final UserServiceClient userServiceClient;

    public CallResponse createCallInfo(CreateCallDto dto, String otherUserKey, String token) {

        UserInfo otherUser = userServiceClient.searchUserInfoByUserKey(otherUserKey);
        UserInfo teacher = userServiceClient.searchUserInfo(token);

        String senderName = otherUser.getUserName();
        String receiverName = teacher.getUserName();

        if (dto.getSender().equals("T")) {
            receiverName = otherUser.getUserName();
            senderName = teacher.getUserName();
        }

        Call savedCall = insertCall(dto, teacher.getUserId(), otherUser.getUserId(), senderName, receiverName);
//        Call savedCall = insertCall(dto, 1L, 2L, "선생님", "학부모");
        return CallResponse.of(savedCall);
    }

    public void updateCallInfo(Long callId, RecordResultInfo res) {
        // 데이터베이스에서 해당 엔티티를 가져옵니다.
        Optional<Call> optionalCall = callRepository.findById(callId);

        if (optionalCall.isPresent()) {
            Call call = optionalCall.get();

            call.updateCallInfo(res.getOverallResult(), res.getOverallPercent().get(0), res.getOverallPercent().get(1), res.getOverallPercent().get(2)
                                , res.getOverallResult().equals("negative"));

            Call updatedCall = callRepository.save(call);

            System.out.println(updatedCall);
            List<CallRecord> callRecords = new ArrayList<>();
            res.getDetailsResult().forEach(e -> {
                System.out.println(e);
                CallRecord callDetails = CallRecord.builder()
                        .call(call)
                        .content(e.getContent())
                        .start(e.getStart())
                        .length(e.getLength())
                        .sentiment(e.getSentiment())
                        .neutral(e.getConfidence().get(0))
                        .positive(e.getConfidence().get(1))
                        .negative(e.getConfidence().get(2))
                        .build();
                callRecords.add(callDetails);
            });
            callRecordRepository.saveAll(callRecords);

        } else {
            throw new EntityNotFoundException("Call not found");
        }

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
