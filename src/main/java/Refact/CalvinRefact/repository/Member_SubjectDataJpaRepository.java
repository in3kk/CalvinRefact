package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface Member_SubjectDataJpaRepository extends JpaRepository<Member_Subject,Long> {

    void deleteAllBySubject(Subject subject);

    Long countFirstByMemberAndSubject(Member member, Subject subject);
}
