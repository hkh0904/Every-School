package com.everyschool.callservice.api.controller.usercall;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.client.VoiceAiServiceClient;
import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.client.response.RecordStartInfo;
import com.everyschool.callservice.api.client.response.RecordStopInfo;
import com.everyschool.callservice.api.controller.FileStore;
import com.everyschool.callservice.api.controller.usercall.request.CreateUserCallRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStartRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStopRequest;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.usercall.UserCallService;
import com.everyschool.callservice.api.service.usercall.dto.CreateUserCallDto;
import com.everyschool.callservice.domain.usercall.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/calls")
public class UserCallController {

    private final UserCallService userCallService;

    private final FileStore fileStore;

    private final VoiceAiServiceClient voiceAiServiceClient;

    /**
     * 통화 녹음 시작 API
     * 상대방이 전화 알림을 통해 전화 받을시
     *
     * @param request 통화 녹음에 필요한 정보들
     * @return 녹음 시작
     */
    @PostMapping("/record/start")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RecordStartInfo> createRecordStart(@RequestBody RecordStartRequest request) {
        log.debug("UserCall UserCallController#createRecordStart");
        log.debug("RecordStartRequest={}", request);

        RecordStartInfo res = voiceAiServiceClient.recordStart(request);
        log.debug("RecordStartInfo={}", res);

        return ApiResponse.created(res);
    }

    /**
     * 통화 녹음 종료 API
     *
     * @param request 통화 녹음에 필요한 정보들
     * @return 종료 정보
     */
    @PostMapping("/record/stop")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RecordStopInfo> createRecordStop(@RequestBody RecordStopRequest request,
                                                        @RequestHeader("Authorization") String token) {
        log.debug("UserCall UserCallController#createRecordStop");
        log.debug("RecordStopRequest={}", request);
        log.debug("token={}", token);

        RecordStopInfo res = voiceAiServiceClient.recordStop(request);
        log.debug("RecordStartInfo={}", res);

        userCallService.createCallInfo(request.toDto(), request.getOtherUserKey(), token);

        return ApiResponse.created(res);
    }

    /**
     * 통화 내역 등록 API
     *
     * @param request 통화 종료후 통화 내역 정보
     * @return 생성 완료 메세지
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createCallInfo(CreateUserCallRequest request,
                                              @RequestHeader("Authorization") String token) throws IOException {
        log.debug("UserCall UserCallController#createCallInfo");
        log.debug("CreateUserCallRequest={}", request);

        // 음성 파일 업로드
        UploadFile uploadFile = fileStore.storeFile(request.getFile());
        log.debug("getUploadFile={}", uploadFile);

        CreateUserCallDto dto = request.toDto();
        dto.setStoreFileName(uploadFile.getStoreFileName());
        dto.setUploadFileName(uploadFile.getUploadFileName());
        dto.setIsBad(false);

        UserCallResponse response = userCallService.createCallInfo(dto, request.getOtherUserKey(), token);
        log.debug("SavedCallResponse={}", response);

//        createRecordAnalysis(request.getFile(), response.getCallId());

        return ApiResponse.created("통화 내용 저장 완료.");
    }

    /*
     * TODO 배치 서버 돌리기
     *
     */
    @Async
    public void createRecordAnalysis(MultipartFile file, Long callId) throws IOException {
        try {
            RecordResultInfo res = voiceAiServiceClient.recordAnalysis(file);

            userCallService.updateCallInfo(callId, res);

            System.out.println(res);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     * 음성 파일 다운로드
     */
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
//        return fileStore.getObject(fileName);
//    }

    /**
     * 통화 상세 기록 등록 API
     *
     * @param request 통화 종료후 통화 내역 정보
     * @return 생성 완료 메세지
     */
//    @PostMapping("/")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ApiResponse<String> createCallInfo(@RequestBody CreateUserCallRequest request,
//                                              @RequestHeader("Authorization") String token) {
//        log.debug("UserCall UserCallController#createCallInfo");
//        log.debug("CreateUserCallRequest={}", request);
//
//        UserCallResponse response = callService.createCallInfo(request.toDto(), request.getOtherUserKey(), token);
//        log.debug("SavedCallResponse={}", response);
//
//        return ApiResponse.created("통화 내용 저장 완료.");
//    }


}
