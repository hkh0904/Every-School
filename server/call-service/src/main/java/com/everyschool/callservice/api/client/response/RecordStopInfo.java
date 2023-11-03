package com.everyschool.callservice.api.client.response;

import lombok.Data;

@Data
public class RecordStopInfo {
    private String cname;
    private String uid;
    private String resourceId;
    private String sid;
    private String fileDir;
    private String uploadStatus;
}
