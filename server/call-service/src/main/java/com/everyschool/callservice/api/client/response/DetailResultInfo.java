package com.everyschool.callservice.api.client.response;

import lombok.Data;

import java.util.List;

@Data
public class DetailResultInfo {

    private String fileName;
    private String content;
    private int start;
    private int length;
    private String sentiment;
    private List<Float> confidence;

}
