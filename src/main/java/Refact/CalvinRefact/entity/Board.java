package Refact.CalvinRefact.entity;

import Refact.CalvinRefact.entity.baseEntity.BaseEntity;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * board_code -> board_id
 * writed_date -> createdDate
 */
@Entity @Getter
public class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String contents;

    @Enumerated(EnumType.STRING)
    private Board_Type board_type;

    @OneToMany(mappedBy = "board")
    private List<File> files = new ArrayList<>();


}
