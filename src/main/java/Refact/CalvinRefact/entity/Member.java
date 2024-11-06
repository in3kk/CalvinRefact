package Refact.CalvinRefact.entity;

import Refact.CalvinRefact.entity.baseEntity.BaseEntity;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * member_code -> member_id
 * join_date -> createdDate
 */
@Entity @Getter @Setter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id",updatable = false)
    private Long id;

    @Column(length = 40, unique = true)
    private String email;

    @Column(length = 40, nullable = false)
    private String pwd;

    @Column(length = 20, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "member_type")
    private Member_Type memberType;

    @Column(nullable = false)
    private LocalDate  birth;

    @Column(nullable = false)
    private String phone_number;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Member_Subject> member_subjects = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Subject> subjects = new ArrayList<>();

    public Member() {
    }

    public Member(String email, String pwd, String name, Member_Type memberType, LocalDate birth, String phone_number, Address address) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.memberType = memberType;
        this.birth = birth;
        this.phone_number = phone_number;
        this.address = address;
    }
}
