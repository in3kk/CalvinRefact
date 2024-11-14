package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectDataJpaRepository extends JpaRepository<Subject,Long> {

}
