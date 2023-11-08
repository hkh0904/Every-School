package com.everyschool.callservice.domain.callrecord.repository;

import com.everyschool.callservice.domain.callrecord.UserCallDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCallRecordRepository extends JpaRepository<UserCallDetails, Long> {
}
