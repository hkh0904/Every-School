package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.api.client.response.SchoolUserInfo;

/**
 * 신고자 정보 생성 클래스
 *
 * @author 임우택
 */
public class WitnessGenerator {

    private static final String STUDENT_FORMAT = "%d학년 %d반 %d번 %s 학생";
    private static final String TEACHER_FORMAT = "%d학년 %d반 %s 선생님";

    /**
     * 신고자 정보 생성
     *
     * @param info 학급 정보
     * @param name 신고자 이름
     * @return 신고자 정보
     */
    public static String createWitnessInfo(SchoolUserInfo info, String name) {
        if (isExistStudentNumber(info)) {
            int number = getNumber(info.getStudentNum());
            return String.format(STUDENT_FORMAT, info.getGrade(), info.getClassNum(), number, name);
        }

        return String.format(TEACHER_FORMAT, info.getGrade(), info.getClassNum(), name);
    }

    /**
     * 학번 존재 여부 체크
     *
     * @param info 학급 정보
     * @return 학번이 존재 하면 true
     */
    private static boolean isExistStudentNumber(SchoolUserInfo info) {
        return info.getStudentNum() != null;
    }

    /**
     * 학번에서 학생 고유 번호 추출
     *
     * @param studentNumber 학번
     * @return 학생 고유 번호
     */
    private static int getNumber(int studentNumber) {
        return studentNumber % 100;
    }
}
