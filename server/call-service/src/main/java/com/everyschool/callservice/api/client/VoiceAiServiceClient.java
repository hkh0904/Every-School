package com.everyschool.callservice.api.client;

import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.client.response.RecordStartInfo;
import com.everyschool.callservice.api.client.response.RecordStopInfo;
import com.everyschool.callservice.api.controller.usercall.request.RecordStartRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStopRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@FeignClient(name = "voiceai-service", url = "http://every-school.com:8000/voiceai-service")
@FeignClient(name = "voiceai-service", url = "http://every-school.com:9002")
public interface VoiceAiServiceClient {

    @PostMapping("/v1/record/start")
    RecordStartInfo recordStart(@RequestBody RecordStartRequest request);

    @PostMapping("/v1/record/stop")
    RecordStopInfo recordStop(@RequestBody RecordStopRequest request);

    @RequestMapping(value = "/v1/record/analysis", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    RecordResultInfo recordAnalysis(MultipartFile file) throws IOException;

}
