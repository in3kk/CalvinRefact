package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDataJpaRepository extends JpaRepository<Member,Long> {
}
