package com.everyschool.userservice.api.controller.user;

import com.everyschool.userservice.api.ApiResponse;
import com.everyschool.userservice.api.controller.user.request.EditPwdRequest;
import com.everyschool.userservice.api.controller.user.request.WithdrawalRequest;
import com.everyschool.userservice.api.controller.user.response.UserResponse;
import com.everyschool.userservice.api.controller.user.response.WithdrawalResponse;
import com.everyschool.userservice.api.service.user.UserService;
import com.everyschool.userservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 명령 API 컨트롤러
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;
    private final TokenUtils tokenUtils;

    /**
     * 회원 비밀번호 수정 API
     *
     * @param request 수정할 비밀번호 정보
     * @return 변경 성공 메세지
     */
    @PatchMapping("/pwd")
    public ApiResponse<String> editPwd(@RequestBody EditPwdRequest request) {
        String userKey = tokenUtils.getUserKey();

        UserResponse response = userService.editPwd(userKey, request.getCurrentPwd(), request.getNewPwd());

        return ApiResponse.of(HttpStatus.OK, "비밀번호가 변경되었습니다.", null);
    }

    /**
     * 회원 탈퇴 API
     *
     * @param request 탈퇴할 회원의 정보
     * @return 탈퇴 성공 메세지
     */
    @PostMapping("/withdrawal")
    public ApiResponse<WithdrawalResponse> withdrawal(@RequestBody WithdrawalRequest request) {
        String userKey = tokenUtils.getUserKey();

        WithdrawalResponse response = userService.withdrawal(userKey, request.getPwd());

        return ApiResponse.of(HttpStatus.OK, "회원 탈퇴가 되었습니다.", response);
    }
}
