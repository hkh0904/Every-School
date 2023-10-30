package com.everyschool.alarmservice.domain.alarm.repository;

import com.everyschool.alarmservice.domain.alarm.AlarmMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmMasterRepository extends JpaRepository<AlarmMaster, Long> {
}
