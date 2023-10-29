package com.everyschool.userservice.domain.user.repository;

import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.service.user.dto.SearchEmailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.everyschool.userservice.domain.user.QUser.user;

/**
 * 회원 Querydsl 클래스
 *
 * @author 임우택
 */
@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 이메일 사용 여부 조회
     *
     * @param email 조회할 이메일
     * @return 이미 사용중이라면 true
     */
    public boolean existEmail(String email) {
        Long result = queryFactory
            .select(user.id)
            .from(user)
            .where(user.email.eq(email))
            .fetchFirst();
        return result != null;
    }

    /**
     * 회원 고유키로 회원 기본 정보 조회
     *
     * @param userKey 조회할 회원 고유키
     * @return 회원 기본 정보
     */
    public Optional<UserInfoResponse> findByUserKey(String userKey) {
        UserInfoResponse content = queryFactory
            .select(Projections.constructor(
                UserInfoResponse.class,
                user.name,
                user.email,
                user.name,
                user.birth,
                user.createdDate
            ))
            .from(user)
            .where(user.userKey.eq(userKey))
            .fetchOne();
        return Optional.ofNullable(content);
    }

    /**
     * 이메일 정보 조회
     *
     * @param name 조회할 회원의 이름
     * @param birth 조회할 회원의 생년월일
     * @return 이메일 정보
     */
    public Optional<SearchEmailDto> findEmailByNameAndBirth(String name, String birth) {
        SearchEmailDto content = queryFactory
            .select(
                Projections.constructor(SearchEmailDto.class,
                    user.email,
                    user.isDeleted
                )
            )
            .from(user)
            .where(
                user.name.eq(name),
                user.birth.eq(birth)
            )
            .fetchOne();
        return Optional.ofNullable(content);
    }

    /**
     * 회원 PK 조회
     *
     * @param userKey 회원 고유키
     * @return 회원 PK
     */
    public Optional<UserClientResponse> findIdByUserKey(String userKey) {
        UserClientResponse content = queryFactory
            .select(Projections.constructor(
                UserClientResponse.class,
                user.id
            ))
            .from(user)
            .where(user.userKey.eq(userKey))
            .fetchOne();
        return Optional.ofNullable(content);
    }
}
