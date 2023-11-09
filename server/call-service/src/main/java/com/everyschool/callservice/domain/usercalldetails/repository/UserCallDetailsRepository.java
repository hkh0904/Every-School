package com.everyschool.callservice.domain.usercalldetails.repository;

import com.everyschool.callservice.domain.usercalldetails.UserCallDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCallDetailsRepository extends JpaRepository<UserCallDetails, Long> {
}
