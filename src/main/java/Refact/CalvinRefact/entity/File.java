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

    private String original_name;

    private String save_name;

    private int size;

    private YN delete_yn;

    private LocalDateTime deleted_date;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
