package com.everyschool.schoolservice.api.app.service.schoolapply;

public class InformationGenerator {

    private static final String STUDENT_INFO_FORMAT = "%d학년 %d반 %s 학생";

    public static String createInformation(int grade, int classNum, String name) {
        return String.format(STUDENT_INFO_FORMAT, grade, classNum, name);
    }
}
