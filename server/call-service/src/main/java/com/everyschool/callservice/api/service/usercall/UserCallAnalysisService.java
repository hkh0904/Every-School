package com.everyschool.callservice.api.service.usercall;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.everyschool.callservice.api.client.VoiceAiServiceClient;
import com.everyschool.callservice.api.client.response.DetailResultInfo;
import com.everyschool.callservice.api.client.response.RecordResultInfo;
import com.everyschool.callservice.api.controller.usercall.request.TsFileKeyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserCallAnalysisService {

    private final AmazonS3 amazonS3;
    private final VoiceAiServiceClient voiceAiServiceClient;
    private final UserCallService userCallService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Async
    public void analyze(Long callId, String filDir){

        log.debug("UserCall UserCallAnalysisService#analyze");
        // M3U8 파일 다운로드
        S3Object s3Object = amazonS3.getObject(bucket, filDir);
        log.debug("s3Object = {}", s3Object);

        try (S3ObjectInputStream inputStream = s3Object.getObjectContent();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<String> tsFileNames = reader.lines()
                    .filter(line -> line.endsWith(".ts"))
                    .collect(Collectors.toList());

            RecordResultInfo finalResult = getFinalInfo(tsFileNames, filDir);
            log.debug("finalResult = {}", finalResult);

            userCallService.updateCallInfo(callId, finalResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String findMaxOverallResult(List<Float> finalOverallPercent) {
        Float maxPercent = Collections.max(finalOverallPercent);
        int index = finalOverallPercent.indexOf(maxPercent);

        if (index == 0) {
            return "neutral";
        }

        if (index == 1) {
            return "positive";
        }

        return "negative";
    }

    private RecordResultInfo getFinalInfo(List<String> tsFileNames, String filDir) {
        List<RecordResultInfo> results = new ArrayList<>();
        float totalNeutral = 0, totalPositive = 0, totalNegative = 0;
        int count = 0;

        for (String tsFileName : tsFileNames) {
            String tsFileKey = filDir.substring(0, filDir.lastIndexOf('/') + 1) + tsFileName;
            log.debug("tsFileKey = {}", tsFileKey);

            TsFileKeyRequest request = TsFileKeyRequest.builder()
                    .tsFileKey(tsFileKey)
                    .build();

            RecordResultInfo result = voiceAiServiceClient.recordAnalysis(request);
            if (result.getDetailsResult() == null) {
                continue;
            }
            log.debug("RecordResultInfo = {}", result);

            results.add(result);

            totalNeutral += result.getOverallPercent().get(0);
            totalPositive += result.getOverallPercent().get(1);
            totalNegative += result.getOverallPercent().get(2);
            count++;
        }

        List<Float> finalOverallPercent = Arrays.asList(totalNeutral/count, totalPositive/count, totalNegative/count);

        List<DetailResultInfo> finalDetailsResult = results.stream()
                .flatMap(result -> result.getDetailsResult().stream())
                .collect(Collectors.toList());

        RecordResultInfo finalResult = new RecordResultInfo();
        finalResult.setOverallResult(findMaxOverallResult(finalOverallPercent));
        finalResult.setOverallPercent(finalOverallPercent);
        finalResult.setDetailsResult(finalDetailsResult);

        return finalResult;
    }
}
