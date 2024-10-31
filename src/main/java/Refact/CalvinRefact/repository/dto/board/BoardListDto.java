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
    private Long file_code1;
    private Long file_code2;
    private Long file_code3;
    private Long file_code4;
    private Long file_code5;
    private String name;
    private Board_Type board_type;
    private String board_thumbnail;

    public BoardListDto() {
    }

    public BoardListDto(Long board_code, Long member_code, String title, String contents, LocalDateTime created_date, Long file_code1, Long file_code2, Long file_code3, Long file_code4, Long file_code5, String name, Board_Type board_type, String board_thumbnail) {
        this.board_code = board_code;
        this.member_code = member_code;
        this.title = title;
        this.contents = contents;
        this.created_date = created_date;
        this.file_code1 = file_code1;
        this.file_code2 = file_code2;
        this.file_code3 = file_code3;
        this.file_code4 = file_code4;
        this.file_code5 = file_code5;
        this.name = name;
        this.board_type = board_type;
        this.board_thumbnail = board_thumbnail;
    }
}
