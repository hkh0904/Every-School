package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
