package Refact.CalvinRefact.repository.dto.board;

import Refact.CalvinRefact.entity.File;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDetailDto {

    private Long board_id;
    //member_name;
    private String name;
    private String title;
    private String contents;
    private Board_Type boardType;
    private List<FileSimpleDto> files;
    private String thumbnail;
    private LocalDateTime created_date;

    public BoardDetailDto(Long board_id, String name, String title, String contents, Board_Type boardType, List<FileSimpleDto> files, LocalDateTime created_date) {
        this.board_id = board_id;
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.boardType = boardType;
        this.files = files;
        this.created_date = created_date;
    }

    public BoardDetailDto(Long board_id, String name, String title, String contents, Board_Type boardType, List<FileSimpleDto> files) {
        this.board_id = board_id;
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.boardType = boardType;
        this.files = files;
    }

    public BoardDetailDto(Long board_id, String name, String title, String contents, Board_Type boardType) {
        this.board_id = board_id;
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.boardType = boardType;
    }


    public BoardDetailDto() {
    }
}
