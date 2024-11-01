package Refact.CalvinRefact.entity;

import Refact.CalvinRefact.entity.baseEntity.BaseEntity;
import Refact.CalvinRefact.entity.entityEnum.YN;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * board 방식 변경
 * file_cod -> file_id
 * created_date -> createdDate
 */
@Entity @Getter
public class File extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(length = 270, nullable = false)
    private String original_name;

    @Column(length = 270, unique = true)
    private String save_name;

    @Column(nullable = false)
    private int size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YN delete_yn;

    private LocalDateTime deleted_date;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public File() {
    }

    public File(String original_name, String save_name, int size, YN delete_yn, Board board) {
        this.original_name = original_name;
        this.save_name = save_name;
        this.size = size;
        this.delete_yn = delete_yn;
        this.board = board;
    }
}
