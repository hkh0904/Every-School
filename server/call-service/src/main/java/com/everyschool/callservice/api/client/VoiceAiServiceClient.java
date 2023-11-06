package com.everyschool.callservice.api.client;

import com.everyschool.callservice.api.client.response.RecordStartInfo;
import com.everyschool.callservice.api.client.response.RecordStopInfo;
import com.everyschool.callservice.api.controller.call.request.RecordStartRequest;
import com.everyschool.callservice.api.controller.call.request.RecordStopRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "voiceai-service", url = "http://k9c108.p.ssafy.io:8000/voiceai-service")
@FeignClient(name = "voiceai-service", url = "http://localhost:8000/voiceai-service")
public interface VoiceAiServiceClient {

    @PostMapping("/v1/record/start")
    RecordStartInfo recordStart(@RequestBody RecordStartRequest request);

    @PostMapping("/v1/record/stop")
    RecordStopInfo recordStop(@RequestBody RecordStopRequest request);

}
