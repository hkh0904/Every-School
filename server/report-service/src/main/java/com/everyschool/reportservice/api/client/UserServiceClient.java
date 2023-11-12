package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * User microservice 통신
 *
 * @author 임우택
 */
@FeignClient(name = "user-service", url = "https://every-school.com/api")
public interface UserServiceClient {

    /**
     * 회원 기본 정보 조회
     *
     * @param userKey 회원 고유키
     * @return 회원 아이디, 회원 유형, 이름, 학급 정보(존재하지 않으면 null)
     */
    @GetMapping("/user-service/client/v1/user-info/{userKey}")
    UserInfo searchByUserKey(@PathVariable(name = "userKey") String userKey);

    /**
     * 회원 기본 정보 조회
     *
     * @param userId 회원 아이디
     * @return 회원 아이디, 회원 유형, 이름, 학급 정보(존재하지 않으면 null)
     */
    @GetMapping("/user-service/client/v1/user-info/{userId}/user-id")
    UserInfo searchByUserId(@PathVariable(name = "userId") Long userId);
}
