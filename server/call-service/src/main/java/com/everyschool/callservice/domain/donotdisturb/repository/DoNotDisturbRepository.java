package com.everyschool.callservice.domain.donotdisturb.repository;

import com.everyschool.callservice.domain.donotdisturb.DoNotDisturb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoNotDisturbRepository extends JpaRepository<DoNotDisturb, Long> {
}
