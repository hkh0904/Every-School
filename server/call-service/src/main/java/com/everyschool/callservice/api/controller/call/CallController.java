package com.everyschool.callservice.api.controller.call;

import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.FileStore;
import com.everyschool.callservice.api.controller.call.request.CreateCallRequest;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.CallService;
import com.everyschool.callservice.api.service.call.dto.CreateCallDto;
import com.everyschool.callservice.domain.call.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/call-service/v1/calls")
public class CallController {

    private final CallService callService;

    private final FileStore fileStore;
    /**
     * 통화 내역 등록 API
     *
     * @param request 통화 종료후 통화 내역 정보
     * @return 생성 완료 메세지
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createCallInfo(CreateCallRequest request,
                                              @RequestHeader("Authorization") String token) throws IOException {
        log.debug("Call CallController#createCallInfo");
        log.debug("CreateCallRequest={}", request);

        UploadFile uploadFile = fileStore.storeFile(request.getFile());
        log.debug("getUploadFile={}", uploadFile);

        CreateCallDto dto = request.toDto();
        dto.setStoreFileName(uploadFile.getStoreFileName());
        dto.setUploadFileName(uploadFile.getUploadFileName());
        dto.setIsBad(false);

        CallResponse response = callService.createCallInfo(dto, request.getOtherUserKey(), token);
        log.debug("SavedCallResponse={}", response);

        return ApiResponse.created("통화 내용 저장 완료.");
    }

    /**
     * 음성 파일 다운로드
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
        return fileStore.getObject(fileName);
    }

    /**
     * 통화 상세 기록 등록 API
     *
     * @param request 통화 종료후 통화 내역 정보
     * @return 생성 완료 메세지
     */
//    @PostMapping("/")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ApiResponse<String> createCallInfo(@RequestBody CreateCallRequest request,
//                                              @RequestHeader("Authorization") String token) {
//        log.debug("Call CallController#createCallInfo");
//        log.debug("CreateCallRequest={}", request);
//
//        CallResponse response = callService.createCallInfo(request.toDto(), request.getOtherUserKey(), token);
//        log.debug("SavedCallResponse={}", response);
//
//        return ApiResponse.created("통화 내용 저장 완료.");
//    }
}
