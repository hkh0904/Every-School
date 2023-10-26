package com.everyschool.schoolservice.domain.school.repository;

import com.everyschool.schoolservice.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
