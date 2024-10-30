package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataJpaRepository extends JpaRepository<File,Long> {
}
