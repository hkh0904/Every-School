package com.everyschool.callservice.domain.call.repository;

import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.everyschool.callservice.domain.call.QCall.call;

@Repository
public class CallQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CallQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CallResponse> findAllByTeacherId(Long userId) {
        return queryFactory
                .select(Projections.constructor(
                        CallResponse.class,
                        call.senderName,
                        call.receiverName,
                        call.sender,
                        call.startDateTime,
                        call.endDateTime,
                        call.uploadFileName,
                        call.storeFileName,
                        call.isBad
                ))
                .from(call)
                .where(
                        call.teacherId.eq(userId)
                )
                .fetch();
    }

    public List<CallResponse> findAllById(Long userId) {
        return queryFactory
                .select(Projections.constructor(
                        CallResponse.class,
                        call.senderName,
                        call.receiverName,
                        call.sender,
                        call.startDateTime,
                        call.endDateTime,
                        call.uploadFileName,
                        call.storeFileName,
                        call.isBad
                ))
                .from(call)
                .where(
                        call.otherUserId.eq(userId)
                )
                .fetch();
    }
}
