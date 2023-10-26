package com.everyschool.alarmservice.api.client.user.resquest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserIdRequest {

    private List<String> userKeys = new ArrayList<>();

    @Builder
    private UserIdRequest(List<String> userKeys) {
        this.userKeys = userKeys;
    }
}
