package com.everyschool.userservice.domain.codedetail.repository;

import com.everyschool.userservice.domain.codedetail.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, Integer> {

    @Query("select c from CodeDetail c join fetch CodeGroup g where c.id=:id")
    Optional<CodeDetail> findWithCodeGroupById(@Param("id") int id);
}
