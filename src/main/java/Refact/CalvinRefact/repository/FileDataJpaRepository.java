package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.File;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileDataJpaRepository extends JpaRepository<File,Long> {

    @Query("select new Refact.CalvinRefact.repository.dto.file.FileSimpleDto(f.id,f.original_name,f.save_name, f.size) from File f join f.board b where b.id = :board_id")
    FileSimpleDto findSimpleFileByBoardId(@Param("board_id") Long id);
}
