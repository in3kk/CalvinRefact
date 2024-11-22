package Refact.CalvinRefact.repository.dto.board;

import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardListDto {

    /**
     * BoardView -> BoardListDto
     */
    private Long board_code;
    private Long member_code;
    private String title;
    private String contents;
    private LocalDateTime created_date;
    private String name;
    private Board_Type board_type;
    private String board_thumbnail;

    public BoardListDto() {
    }

    public BoardListDto(Long board_code, Long member_code, String title, String contents, LocalDateTime created_date, String name, Board_Type board_type, String board_thumbnail) {
        this.board_code = board_code;
        this.member_code = member_code;
        this.title = title;
        this.contents = contents;
        this.created_date = created_date;
        this.name = name;
        this.board_type = board_type;
        this.board_thumbnail = board_thumbnail;
    }

    public BoardListDto(Long board_code, Long member_code, String title, LocalDateTime created_date, String name, Board_Type board_type, String board_thumbnail) {
        this.board_code = board_code;
        this.member_code = member_code;
        this.title = title;
        this.created_date = created_date;
        this.name = name;
        this.board_type = board_type;
        this.board_thumbnail = board_thumbnail;
    }

    public BoardListDto(Long board_code, Long member_code, String title, LocalDateTime created_date, String name, Board_Type board_type) {
        this.board_code = board_code;
        this.member_code = member_code;
        this.title = title;
        this.created_date = created_date;
        this.name = name;
        this.board_type = board_type;
    }
}
