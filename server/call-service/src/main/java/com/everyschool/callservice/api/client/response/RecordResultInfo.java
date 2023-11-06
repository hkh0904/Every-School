package com.everyschool.callservice.api.client.response;

import lombok.Data;

import java.util.List;

@Data
public class RecordResultInfo {

    private String overallResult;
    private List<Float> overallPercent;

    private List<DetailResultInfo> detailsResult;
}
