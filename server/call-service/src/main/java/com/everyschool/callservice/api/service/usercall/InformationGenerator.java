package com.everyschool.callservice.api.service.usercall;

import com.everyschool.callservice.api.client.response.StudentSchoolClassInfo;

public abstract class InformationGenerator {

    private static final String PARENT_INFO = "%d학년 %d반 %d번 %s %s";
    private static final String TEACHER_INFO = "%d학년 %d반 %s 선생님";
    private static final String FATHER = "아버님";
    private static final String MOTHER = "어머님";

    /**
     * 학부모 정보 생성
     *
     * @param info        자녀 학급 정보
     * @param studentName 자녀 이름
     * @param parentType  학부모 타입
     * @return 생성된 학부모 정보
     */
    public static String createParentInfo(StudentSchoolClassInfo info, String studentName, char parentType) {
        String parentText = convertParentText(parentType);

        int studentNumber = getStudentNumber(info.getStudentNum());

        return String.format(PARENT_INFO, info.getGrade(), info.getClassNum(), studentNumber, studentName, parentText);
    }

    /**
     * 교직원 정보 생성
     *
     * @param info        교직원 학급 정보
     * @param teacherName 교직원 이름
     * @return 생성된 교직원 정보
     */
    public static String createTeacherInfo(StudentSchoolClassInfo info, String teacherName) {
        return String.format(TEACHER_INFO, info.getGrade(), info.getClassNum(), teacherName);
    }

    /**
     * 학부모 타입 텍스트 전환
     *
     * @param parentType 학부모 타입
     * @return 학부모 타입 텍스트
     */
    private static String convertParentText(char parentType) {
        if (parentType == 'M') {
            return FATHER;
        }
        return MOTHER;
    }

    /**
     * 학생 고유 번호 추출
     *
     * @param studentNum 학번
     * @return 추출된 학생 고유 번호
     */
    private static int getStudentNumber(int studentNum) {
        return studentNum % 100;
    }
}
