package com.everyschool.callservice.api.client.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RecordStartInfo {

    private String cname;
    private String uid;
    private String resourceId;
    private String sid;

    @Builder
    private RecordStartInfo(String cname, String uid, String resourceId, String sid) {
        this.cname = cname;
        this.uid = uid;
        this.resourceId = resourceId;
        this.sid = sid;
    }
}
