package com.everyschool.callservice.api.client;

import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.client.response.RecordStartInfo;
import com.everyschool.callservice.api.client.response.RecordStopInfo;
import com.everyschool.callservice.api.controller.usercall.request.RecordStartRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStopRequest;
import com.everyschool.callservice.api.controller.usercall.request.TsFileKeyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "voiceai-service", url = "http://every-school.com:9002")
public interface VoiceAiServiceClient {

    @PostMapping("/v1/record/start")
    RecordStartInfo recordStart(@RequestBody RecordStartRequest request);

    @PostMapping("/v1/record/stop")
    RecordStopInfo recordStop(@RequestBody RecordStopRequest request);

    @PostMapping(value = "/v1/record/analysis")
    RecordResultInfo recordAnalysis(@RequestBody TsFileKeyRequest request);
}
