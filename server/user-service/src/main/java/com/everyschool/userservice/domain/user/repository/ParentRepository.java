package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.domain.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
