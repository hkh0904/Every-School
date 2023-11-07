package com.everyschool.callservice.domain.callrecord.repository;

import com.everyschool.callservice.domain.callrecord.CallRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRecordRepository extends JpaRepository<CallRecord, Long> {
}
