package com.everyschool.schoolservice.domain.schooluser.repository;

import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Long> {
}
