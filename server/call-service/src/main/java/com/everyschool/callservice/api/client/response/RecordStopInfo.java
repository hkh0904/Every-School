package com.everyschool.callservice.api.client.response;

import lombok.Builder;
import lombok.Data;

@Data
public class RecordStopInfo {
    private String cname;
    private String uid;
    private String resourceId;
    private String sid;
    private String fileDir;
    private String uploadStatus;

    @Builder
    private RecordStopInfo(String cname, String uid, String resourceId, String sid, String fileDir, String uploadStatus) {
        this.cname = cname;
        this.uid = uid;
        this.resourceId = resourceId;
        this.sid = sid;
        this.fileDir = fileDir;
        this.uploadStatus = uploadStatus;
    }
}
