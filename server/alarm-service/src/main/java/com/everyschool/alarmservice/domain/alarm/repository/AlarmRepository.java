package com.everyschool.alarmservice.domain.alarm.repository;

import com.everyschool.alarmservice.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
