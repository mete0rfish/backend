package com.onetool.server.api.member.repository;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    @Query(value = "SELECT count(*) FROM Member m")
    Long countAllMember();

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String id);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.cart WHERE m.id = :id")
    Optional<Member> findByIdWithCart(@Param("id") Long id);

    @Query("SELECT m FROM Member m WHERE m.name = :name AND m.phoneNum = :phoneNum")
    Optional<Member> findByNameAndPhoneNum(@Param("name") String name, @Param("phoneNum") String phoneNum);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m JOIN FETCH m.qnaBoards WHERE m.id = :memberId")
    Optional<Member> findMemberWithQnaBoards(@Param("memberId") Long memberId);

    @Query(value = "SELECT * FROM member m WHERE m.id = :id AND m.is_deleted = :isDeleted", nativeQuery = true)
    Optional<Member> findByIdAndIsDeleted(@Param("id") Long id, @Param("isDeleted") boolean isDeleted);
}