package com.everyschool.schoolservice.domain.school.repository;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.School;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SchoolQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SchoolQueryRepository schoolQueryRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @DisplayName("DB로부터 학교 이름으로 목록 조회")
    @Test
    void findByName() {
        // given
        String search = "수완중";
        School school1 = School.builder()
                .name("경기수완중학교")
                .zipcode("12345")
                .address("경기도")
                .url("https://www.asdf.com")
                .openDate(LocalDateTime.now().minusDays(10))
                .codeId(1)
                .build();
        School school2 = School.builder()
                .name("인천수완중학교")
                .zipcode("12345")
                .address("경기도")
                .url("https://www.asdf.com")
                .openDate(LocalDateTime.now().minusDays(10))
                .codeId(1)
                .build();
        School school3 = School.builder()
                .name("광주수완중학교")
                .zipcode("12345")
                .address("경기도")
                .url("https://www.asdf.com")
                .openDate(LocalDateTime.now().minusDays(10))
                .codeId(1)
                .build();
        School school4 = School.builder()
                .name("청량중학교")
                .zipcode("12345")
                .address("경기도")
                .url("https://www.asdf.com")
                .openDate(LocalDateTime.now().minusDays(10))
                .codeId(1)
                .build();

        schoolRepository.save(school1);
        schoolRepository.save(school2);
        schoolRepository.save(school3);
        schoolRepository.save(school4);

        // when
        List<SchoolResponse> responses = schoolQueryRepository.findByName(search);

        // then
        assertThat(responses.size()).isEqualTo(3);
        assertThat(responses.get(0).getName()).isEqualTo("경기수완중학교");
    }
}