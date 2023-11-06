package com.everyschool.callservice.domain.call.repository;

import com.everyschool.callservice.domain.call.UserCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCallRepository extends JpaRepository<UserCall, Long> {
}
