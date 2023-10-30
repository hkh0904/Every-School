package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.domain.consult.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
}
