package com.everyschool.callservice.domain.call.repository;

import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.callservice.domain.call.QUserCall.userCall;

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
                        userCall.sender,
                        userCall.startDateTime,
                        userCall.endDateTime,
                        userCall.isBad
                ))
                .from(userCall)
                .where(
                        userCall.teacherId.eq(userId)
                )
                .fetch();
    }

    public List<UserCallResponse> findAllById(Long userId) {
        return queryFactory
                .select(Projections.constructor(
                        UserCallResponse.class,
                        userCall.id,
                        userCall.senderName,
                        userCall.receiverName,
                        userCall.sender,
                        userCall.startDateTime,
                        userCall.endDateTime,
                        userCall.isBad
                ))
                .from(userCall)
                .where(
                        userCall.otherUserId.eq(userId)
                )
                .fetch();
    }

    public UserCallResponse findById(Long userId) {
        return queryFactory
                .select(Projections.constructor(
                        UserCallResponse.class,
                        userCall.id,
                        userCall.senderName,
                        userCall.receiverName,
                        userCall.sender,
                        userCall.startDateTime,
                        userCall.endDateTime,
                        userCall.uploadFileName,
                        userCall.storeFileName,
                        userCall.isBad
                ))
                .from(userCall)
                .where(
                        userCall.otherUserId.eq(userId)
                )
                .fetchOne();
    }
}
