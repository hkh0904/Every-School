package com.everyschool.schoolservice.api.service.school;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;


class SchoolQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private SchoolQueryService schoolQueryService;

    @Autowired
    private SchoolRepository schoolRepository;

    @DisplayName("학교 일부 이름을 통해 학교 목록 가져오기.")
    @Test
    void searchSchools() {
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
        List<SchoolResponse> responses = schoolQueryService.searchSchools(search);

        // then
        assertThat(responses.size()).isEqualTo(3);
        assertThat(responses.get(0).getName()).isEqualTo("경기수완중학교");
    }

    @DisplayName("[예외] 학교 코드 정보가 없을때.")
    @Test
    void searchOneSchoolError() {

        // then
        assertThatThrownBy(() -> schoolQueryService.searchOneSchool(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 학교는 없습니다.");

    }

    @DisplayName("학교 코드를 통해 학교 정보 가져오기.")
    @Test
    void searchOneSchool() {
        // given
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

        School save = schoolRepository.save(school1);
        schoolRepository.save(school2);
        schoolRepository.save(school3);
        schoolRepository.save(school4);

        // when
        SchoolResponse response = schoolQueryService.searchOneSchool(save.getId());

        // then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("경기수완중학교");
    }
}