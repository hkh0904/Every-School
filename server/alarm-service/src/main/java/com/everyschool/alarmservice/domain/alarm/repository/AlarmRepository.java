package com.everyschool.alarmservice.domain.alarm.repository;

import com.everyschool.alarmservice.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("select a from Alarm a join fetch AlarmMaster am where a.id =:alarmId")
    Optional<Alarm> findMasterById(@Param("alarmId") Long alarmId);
}
