package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Member_SubjectDataJpaRepository extends JpaRepository<Member_Subject,Long> {

    void deleteAllBySubject(Subject subject);
}
