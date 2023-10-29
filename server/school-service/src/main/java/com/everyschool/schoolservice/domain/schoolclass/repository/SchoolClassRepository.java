package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    Optional<SchoolClass> findByTeacherIdAndSchoolYear(Long teacherId, Integer schoolYear);
}
