package com.everyschool.schoolservice.domain.schoolapply.repository;

import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 학급 신청 저장소
 *
 * @author 임우택
 */
@Repository
public interface SchoolApplyRepository extends JpaRepository<SchoolApply, Long> {
}
