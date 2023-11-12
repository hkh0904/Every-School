package com.everyschool.callservice.api.service.usercall;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.usercall.dto.CreateUserCallDto;
import com.everyschool.callservice.domain.usercall.UserCall;
import com.everyschool.callservice.domain.usercall.repository.UserCallRepository;
import com.everyschool.callservice.domain.usercalldetails.UserCallDetails;
import com.everyschool.callservice.domain.usercalldetails.repository.UserCallDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserCallService {

    private final UserCallRepository userCallRepository;
    private final UserCallDetailsRepository userCallDetailsRepository;
    private final UserServiceClient userServiceClient;

    public UserCallResponse createCallInfo(CreateUserCallDto dto, String otherUserKey, String token) {

        UserInfo receiver = userServiceClient.searchUserInfoByUserKey(otherUserKey);
        UserInfo sender = userServiceClient.searchUserInfo(token);

        String senderName = sender.getUserName();
        String receiverName = receiver.getUserName();

        Long teacherId = sender.getUserId();
        Long otherUserId = receiver.getUserId();

        if (dto.getSender().equals("O")) {
            teacherId = receiver.getUserId();
            otherUserId = sender.getUserId();
        }

        UserCall savedUserCall = insertCall(dto, teacherId, otherUserId, senderName, receiverName);
        return UserCallResponse.of(savedUserCall);
    }

    public void updateCallInfo(Long callId, RecordResultInfo res) {
        log.debug("UserCall UserCallService#updateCallInfo");

        Optional<UserCall> optionalCall = userCallRepository.findById(callId);
        log.debug("optionalCall = {}", optionalCall);

        if (optionalCall.isPresent()) {
            UserCall userCall = optionalCall.get();

            userCall.updateCallInfo(res.getOverallResult(), res.getOverallPercent().get(0), res.getOverallPercent().get(1), res.getOverallPercent().get(2)
                    , res.getOverallResult().equals("negative"));

            UserCall updatedUserCall = userCallRepository.save(userCall);
            log.debug("updatedUserCall = {}", updatedUserCall);

            List<UserCallDetails> userCallDetails = new ArrayList<>();

            res.getDetailsResult().forEach(e -> {
                System.out.println(e);
                UserCallDetails callDetails = UserCallDetails.builder()
                        .userCall(userCall)
                        .fileName(e.getFileName())
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
            userCallDetailsRepository.saveAll(userCallDetails);

        } else {
            throw new EntityNotFoundException("UserCall not found");
        }

    }

    private UserCall insertCall(CreateUserCallDto dto, Long teacherId, Long otherUserId, String senderName,
                                String receiverName) {
        UserCall userCall = UserCall.builder()
                .teacherId(teacherId)
                .otherUserId(otherUserId)
                .sender(dto.getSender())
                .senderName(senderName)
                .receiverName(receiverName)
                .receiveCall("Y")
                .startDateTime(dto.getStartDateTime())
                .endDateTime(dto.getEndDateTime())
                .isBad(false)
                .build();

        return userCallRepository.save(userCall);
    }

}
