package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDataJpaRepository extends JpaRepository<Board,Long> {

    Page<Board> findAllByBoardType(Board_Type boardType, Pageable pageable);

    Page<Board> findAllByTitleContaining(String title, Pageable pageable);

    Page<Board> findAllByBoardTypeAndTitleContaining(Board_Type boardType,String title, Pageable pageable);

}
