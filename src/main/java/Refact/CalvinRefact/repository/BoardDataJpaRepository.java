package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardDataJpaRepository extends JpaRepository<Board,Long> {

    Page<Board> findAllByBoardType(Board_Type boardType, Pageable pageable);
    @Query("select distinct b from Board b join fetch b.files where boardType = :boardType")
    Page<Board> findAllFetchByBoardType(@Param("boardType") Board_Type boardType, Pageable pageable);

    Page<Board> findAllByTitleContaining(String title, Pageable pageable);
    @Query("select distinct b from Board b join fetch b.files where b.title like %:title%")
    Page<Board> findAllFetchByTitleContaining(@Param("title") String title, Pageable pageable);

    Page<Board> findAllByBoardTypeAndTitleContaining(Board_Type boardType,String title, Pageable pageable);

}
