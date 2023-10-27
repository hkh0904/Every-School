package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}
