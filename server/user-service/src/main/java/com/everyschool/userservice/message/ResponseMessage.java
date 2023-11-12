package com.everyschool.userservice.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SUCCESS_EDIT_PASSWORD("비밀번호가 변경되었습니다."),
    SUCCESS_WITHDRAWAL("회원 탈퇴가 되었습니다.");

    private final String message;
}
