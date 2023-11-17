package com.everyschool.schoolservice.api.app.service.schoolapply;

import com.everyschool.schoolservice.api.app.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.dto.CreateSchoolApplyDto;
import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassQueryAppRepository;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.schoolservice.api.app.service.schoolapply.InformationGenerator.*;
import static com.everyschool.schoolservice.domain.schoolapply.ApplyType.*;
import static com.everyschool.schoolservice.error.ErrorMessage.NO_SUCH_SCHOOL_CLASS;

/**
 * 앱 학교 소속 신청 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
public class SchoolApplyAppService {

    private final SchoolApplyRepository schoolApplyRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassQueryAppRepository schoolClassAppQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 학교 소속 신청
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param dto        학교 소속 신청 정보
     * @return 신청된 정보
     */
    public CreateSchoolApplyResponse createSchoolApply(String userKey, int schoolYear, Long schoolId, CreateSchoolApplyDto dto) {
        //회원 정보 조회
        UserInfo studentInfo = userServiceClient.searchUserInfo(userKey);

        //학급 정보 조회
        SchoolClass schoolClass = getSchoolClass(schoolYear, schoolId, dto);

        String info = createInformation(schoolClass.getGrade(), schoolClass.getClassNum(), studentInfo.getUserName());
        //학교 신청 엔티티 생성
        SchoolApply schoolApply = createSchoolApplyEntity(schoolYear, studentInfo.getUserId(), schoolClass, info);

        SchoolApply savedSchoolApply = schoolApplyRepository.save(schoolApply);

        return CreateSchoolApplyResponse.of(savedSchoolApply);
    }

    /**
     * 학부모 학교 소속 신청
     *
     * @param parentId      학부모 아이디
     * @param studentId     학생 아이디
     * @param schoolClassId 학급 아이디
     */
    public void createParentSchoolApply(Long parentId, Long studentId, Long schoolClassId) {
        //학부모 정보 조회
        UserInfo parentInfo = userServiceClient.searchUserInfoById(parentId);

        //학생(자식) 정보 조회
        UserInfo studentInfo = userServiceClient.searchUserInfoById(studentId);

        //학급 정보 조회
        SchoolClass schoolClass = getSchoolClassById(schoolClassId);

        String info = createInformation(schoolClass.getGrade(), schoolClass.getClassNum(), 1, studentInfo.getUserName());

        SchoolApply schoolApply = createSchoolApplyEntity(schoolClass.getSchoolYear(), studentInfo.getUserId(), parentId, schoolClass, info, parentInfo.getUserType());

        SchoolApply savedSchoolApply = schoolApplyRepository.save(schoolApply);
    }

    /**
     * 학교 신청 엔티티 생성
     *
     * @param schoolYear  학년도
     * @param userId      회원 아이디
     * @param schoolClass 학급 엔티티
     * @param info        학생 정보
     * @return 생성된 학교 신청 엔티티
     */
    private SchoolApply createSchoolApplyEntity(int schoolYear, Long userId, SchoolClass schoolClass, String info) {
        return SchoolApply.builder()
            .schoolYear(schoolYear)
            .studentInfo(info)
            .codeId(STUDENT.getCode())
            .studentId(userId)
            .parentId(null)
            .schoolClass(schoolClass)
            .build();
    }

    /**
     * 학교 신청 엔티티 생성
     *
     * @param schoolYear  학년도
     * @param userId      회원 아이디
     * @param parentId    학부모 아이디
     * @param schoolClass 학급 아이디
     * @param info        학생 정보
     * @param userType    학부모 유형
     * @return 생성된 학교 신청 엔티티
     */
    private SchoolApply createSchoolApplyEntity(int schoolYear, Long userId, Long parentId, SchoolClass schoolClass, String info, char userType) {
        return SchoolApply.builder()
            .schoolYear(schoolYear)
            .studentInfo(info)
            .codeId(userType == 'M' ? FATHER.getCode() : MOTHER.getCode())
            .studentId(userId)
            .parentId(parentId)
            .schoolClass(schoolClass)
            .build();
    }

    /**
     * 학급 정보로 학급 엔티티 조회
     *
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param dto        학년, 반 정보
     * @return 조회된 학급 엔티티
     * @throws NoSuchElementException 정보가 일치하는 학급이 존재하지 않는 경우 발생
     */
    private SchoolClass getSchoolClass(int schoolYear, Long schoolId, CreateSchoolApplyDto dto) {
        Optional<SchoolClass> findSchoolClass = schoolClassAppQueryRepository.findByInfo(schoolYear, schoolId, dto.getGrade(), dto.getClassNum());
        if (findSchoolClass.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_SCHOOL_CLASS.getMessage());
        }
        return findSchoolClass.get();
    }

    /**
     * 학급 아이디로 학급 엔티티 조회
     *
     * @param schoolClassId 학급 아이디
     * @return 조회된 학급 엔티티
     * @throws NoSuchElementException 정보가 일치하는 학급이 존재하지 않는 경우 발생
     */
    private SchoolClass getSchoolClassById(Long schoolClassId) {
        Optional<SchoolClass> findSchoolClass = schoolClassRepository.findById(schoolClassId);
        if (findSchoolClass.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_SCHOOL_CLASS.getMessage());
        }
        return findSchoolClass.get();
    }
}
