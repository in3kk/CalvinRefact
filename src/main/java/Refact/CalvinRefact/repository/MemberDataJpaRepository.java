package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDataJpaRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndPwd(String email, String pwd);

    Long countByEmail(String email);
}
