package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.domain.user.StudentParent;
import com.everyschool.userservice.domain.user.StudentParentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentParentRepository extends JpaRepository<StudentParent, StudentParentId> {
}
