package com.everyschool.schoolservice.api.app.service.schoolapply;

public class InformationGenerator {

    private static final String STUDENT_INFO_FORMAT = "%d학년 %d반 %s 학생";

    /**
     * 학생 정보 생성
     *
     * @param grade    학년
     * @param classNum 반
     * @param name     학생 이름
     * @return 생성된 학생 정보
     */
    public static String createInformation(int grade, int classNum, String name) {
        return String.format(STUDENT_INFO_FORMAT, grade, classNum, name);
    }
}
