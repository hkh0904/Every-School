package com.everyschool.callservice.domain.usercall.repository;

import com.everyschool.callservice.api.controller.usercall.response.ReportCallsResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallDetailsResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallReportResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.callservice.domain.usercall.QUserCall.userCall;
import static com.everyschool.callservice.domain.usercalldetails.QUserCallDetails.userCallDetails;

@Repository
public class UserCallQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserCallQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<UserCallResponse> findAllByTeacherId(Long userId) {
        return queryFactory
                .select(Projections.constructor(
                        UserCallResponse.class,
                        userCall.id,
                        userCall.senderName,
                        userCall.receiverName,
                        userCall.receiveCall,
                        userCall.sender,
                        userCall.startDateTime,
                        userCall.endDateTime,
                        userCall.isBad
                ))
                .from(userCall)
                .where(
                        userCall.teacherId.eq(userId)
                )
                .orderBy(userCall.startDateTime.desc())
                .fetch();
    }

    public List<UserCallResponse> findAllById(Long userId) {
        return queryFactory
                .select(Projections.constructor(
                        UserCallResponse.class,
                        userCall.id,
                        userCall.senderName,
                        userCall.receiverName,
                        userCall.receiveCall,
                        userCall.sender,
                        userCall.startDateTime,
                        userCall.endDateTime,
                        userCall.isBad
                ))
                .from(userCall)
                .where(
                        userCall.otherUserId.eq(userId)
                )
                .orderBy(userCall.startDateTime.desc())
                .fetch();
    }

    public UserCallReportResponse findByCallId(Long userCallId) {
        return queryFactory
                .select(Projections.constructor(
                        UserCallReportResponse.class,
                        userCall.sentiment.as("overallSentiment"),
                        userCall.neutral.as("overallNeutral"),
                        userCall.positive.as("overallPositive"),
                        userCall.negative.as("overallNegative"),
                        userCall.isBad.as("isBad")
                ))
                .from(userCall)
                .where(
                        userCall.id.eq(userCallId)
                )
                .fetchOne();
    }

    public List<UserCallDetailsResponse> findAllByCallId(Long userCallId) {
        return queryFactory
                .select(Projections.constructor(
                        UserCallDetailsResponse.class,
                        userCallDetails.fileName,
                        userCallDetails.content,
                        userCallDetails.start,
                        userCallDetails.length,
                        userCallDetails.sentiment,
                        userCallDetails.neutral,
                        userCallDetails.positive,
                        userCallDetails.negative
                ))
                .from(userCallDetails)
                .where(
                        userCallDetails.userCall.id.eq(userCallId)
                )
                .fetch();
    }


    public List<ReportCallsResponse> findReportsById(Long userId) {
        System.out.println(userId);
        return queryFactory
                .select(Projections.constructor(
                        ReportCallsResponse.class,
                        userCall.id.as("userCallId"),
                        Expressions.asString("통화").as("type"),
                        new CaseBuilder()
                            .when(userCall.sender.eq("O"))
                            .then(userCall.senderName)
                            .otherwise(userCall.receiverName)
                            .as("reportedName"),
                        userCall.endDateTime.as("reportedTime")
                ))
                .from(userCall)
                .where(
                        userCall.teacherId.eq(userId),
                        userCall.isBad.isTrue()
                )
                .fetch();
    }
}
