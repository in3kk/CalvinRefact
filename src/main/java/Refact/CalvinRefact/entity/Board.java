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
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Column(length = 50,nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Board_Type board_type;

    @OneToOne
    @JoinColumn(name = "file_id",unique = true, insertable = false, updatable = false)
    private File file1;
    @OneToOne
    @JoinColumn(name = "file_id",unique = true, insertable = false, updatable = false)
    private File file2;
    @OneToOne
    @JoinColumn(name = "file_id",unique = true, insertable = false, updatable = false)
    private File file3;
    @OneToOne
    @JoinColumn(name = "file_id",unique = true, insertable = false, updatable = false)
    private File file4;
    @OneToOne
    @JoinColumn(name = "file_id",unique = true, insertable = false, updatable = false)
    private File file5;


    public Board() {
    }

    public Board(Long id, Member member, String title, String contents, Board_Type board_type, File file1, File file2, File file3, File file4, File file5) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.contents = contents;
        this.board_type = board_type;
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.file4 = file4;
        this.file5 = file5;
    }

    public Board(Member member, String title, String contents, Board_Type board_type) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.contents = contents;
        this.board_type = board_type;
    }
}
