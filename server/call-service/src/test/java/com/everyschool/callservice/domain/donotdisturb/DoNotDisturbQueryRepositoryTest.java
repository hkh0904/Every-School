package com.everyschool.callservice.domain.donotdisturb;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.domain.donotdisturb.repository.DoNotDisturbQueryRepository;
import com.everyschool.callservice.domain.donotdisturb.repository.DoNotDisturbRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DoNotDisturbQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DoNotDisturbRepository doNotDisturbRepository;
    @Autowired
    private DoNotDisturbQueryRepository doNotDisturbQueryRepository;


    @DisplayName("방해 금지 목록 가져오기")
    @Test
    void findAllByUserId() {
        // given
        DoNotDisturb doNotDisturb1 = saveDoNot(1L, LocalDateTime.now(), LocalDateTime.now(), true);
        DoNotDisturb doNotDisturb2 = saveDoNot(1L, LocalDateTime.now(), LocalDateTime.now(), false);
        DoNotDisturb doNotDisturb3 = saveDoNot(1L, LocalDateTime.now(), LocalDateTime.now(), true);
        DoNotDisturb doNotDisturb4 = saveDoNot(1L, LocalDateTime.now(), LocalDateTime.now(), true);

        // when
        List<DoNotDisturbResponse> responses = doNotDisturbQueryRepository.findAllByUserId(1L);

        // then
        assertThat(responses).hasSize(4);
        assertThat(responses)
                .extracting("isActivate")
                .containsExactlyInAnyOrder(true, false, true, true);
    }

    private DoNotDisturb saveDoNot(Long teacherId, LocalDateTime startTime, LocalDateTime endTime, Boolean isActivate) {
        DoNotDisturb doNotDisturb = DoNotDisturb.builder()
                .teacherId(teacherId)
                .startTime(startTime)
                .endTime(endTime)
                .isActivate(isActivate)
                .build();
        return doNotDisturbRepository.save(doNotDisturb);
    }
}
