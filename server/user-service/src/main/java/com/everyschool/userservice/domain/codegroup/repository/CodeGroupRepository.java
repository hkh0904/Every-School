package com.everyschool.userservice.domain.codegroup.repository;

import com.everyschool.userservice.domain.codegroup.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long> {
}
