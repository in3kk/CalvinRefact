package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndPwd(String email, String pwd);

    Long countByEmail(String email);

    Optional<Member> findByEmail(String email);

    //멤버 email 검색
    Page<Member> findByEmailContaining(String email, Pageable pageable);

    //멤버 username 검색
    Page<Member> findByNameContaining(String name, Pageable pageable);

    @Query("select m.id from member m where m.email = :email")
    Long findIdByEmail(@Param("email")String email);

}
