package Refact.CalvinRefact.entity;

import Refact.CalvinRefact.entity.baseEntity.BaseEntity;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * member_code -> member_id
 * join_date -> createdDate
 */
@Entity @Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String pwd;

    private String name;

    @Enumerated(EnumType.STRING)
    private Member_Type member_type;

    private LocalDate  birth;

    private String phone_number;

    private String address;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Member_Subject> member_subjects = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Subject> subjects = new ArrayList<>();

    public Member() {
    }

    public Member(String email, String pwd, String name, Member_Type member_type, LocalDate birth, String phone_number, String address) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.member_type = member_type;
        this.birth = birth;
        this.phone_number = phone_number;
        this.address = address;
    }
}