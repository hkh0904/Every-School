package com.everyschool.userservice.domain.codedetail.repository;

import com.everyschool.userservice.domain.codedetail.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, Integer> {
}
