package com.everyschool.alarmservice.domain.alarm.repository;

import com.everyschool.alarmservice.api.controller.alarm.response.AlarmResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.everyschool.alarmservice.domain.alarm.QAlarm.*;
import static com.everyschool.alarmservice.domain.alarm.QAlarmMaster.alarmMaster;

@Repository
public class AlarmQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AlarmQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AlarmResponse> findByRecipientId(Long recipientId) {
        return queryFactory
            .select(Projections.constructor(
                AlarmResponse.class,
                alarm.id,
                alarm.alarmMaster.title,
                alarm.alarmMaster.content,
                alarm.alarmMaster.schoolYear,
                alarm.isRead,
                alarm.createdDate
            ))
            .from(alarm)
            .join(alarm.alarmMaster, alarmMaster)
            .where(
                alarm.recipientId.eq(recipientId),
                alarm.isRead.isTrue()
            )
            .fetch();
    }

    public AlarmResponse findByAlarmId(Long alarmId) {
        return queryFactory
                .select(Projections.constructor(
                        AlarmResponse.class,
                        alarm.id,
                        alarm.alarmMaster.title,
                        alarm.alarmMaster.content,
                        alarm.alarmMaster.schoolYear,
                        alarm.isRead,
                        alarm.createdDate
                ))
                .from(alarm)
                .join(alarm.alarmMaster, alarmMaster)
                .where(
                        alarm.id.eq(alarmId),
                        alarm.isRead.isTrue()
                )
                .fetchOne();
    }
}
