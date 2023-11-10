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


    @DisplayName("최근 등록한 방해 금지 가져오기")
    @Test
    void findByUserId() {
        // given
        DoNotDisturb doNotDisturb1 = saveDoNot(Long.valueOf("1"), 1L, LocalDateTime.now(), LocalDateTime.now(), true);
        DoNotDisturb doNotDisturb2 = saveDoNot(Long.valueOf("2"), 1L, LocalDateTime.now(), LocalDateTime.now(), false);
        DoNotDisturb doNotDisturb3 = saveDoNot(Long.valueOf("3"), 1L, LocalDateTime.now(), LocalDateTime.now(), true);
        DoNotDisturb doNotDisturb4 = saveDoNot(Long.valueOf("4"), 1L, LocalDateTime.now(), LocalDateTime.now(), true);

        // when
        DoNotDisturbResponse response = doNotDisturbQueryRepository.findByUserId(1L);

        // then
        assertThat(response.getDoNotDisturbId()).isEqualTo(doNotDisturb4.getId());
        assertThat(response.isActivate()).isTrue();
    }

    private DoNotDisturb saveDoNot(Long id, Long teacherId, LocalDateTime startTime, LocalDateTime endTime, Boolean isActivate) {
        DoNotDisturb doNotDisturb = DoNotDisturb.builder()
                .id(id)
                .teacherId(teacherId)
                .startTime(startTime)
                .endTime(endTime)
                .isActivate(isActivate)
                .build();
        return doNotDisturbRepository.save(doNotDisturb);
    }
}
