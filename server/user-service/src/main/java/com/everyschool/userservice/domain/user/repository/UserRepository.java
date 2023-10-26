package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 회원 Data JPA 클래스
 *
 * @author 임우택
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 조회할 이메일
     * @return 조회된 회원 엔티티
     */
    Optional<User> findByEmail(String email);
}
