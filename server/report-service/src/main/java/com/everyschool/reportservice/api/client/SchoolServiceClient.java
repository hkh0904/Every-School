package com.everyschool.reportservice.api.client;

import com.everyschool.reportservice.api.client.response.SchoolUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * School microservice 통신
 *
 * @author 임우택
 */
@FeignClient(name = "school-service", url = "https://every-school.com/api")
public interface SchoolServiceClient {

    /**
     * 학급 유저 정보 조회
     *
     * @param userId 회원 아이디
     * @return 학년, 반, 학번(존재하지 않으면 null)
     */
    @GetMapping("/school-service/client/v1/student-info/{userId}")
    SchoolUserInfo searchByUserId(@PathVariable(name = "userId") Long userId);
}
