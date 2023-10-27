package com.everyschool.callservice.domain.call.repository;

import com.everyschool.callservice.domain.call.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
}
