package Refact.CalvinRefact.entity;

import Refact.CalvinRefact.entity.baseEntity.BaseEntity;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * subject_code -> subject_id
 * subject_stat -> ordinal
 */
@Entity @Getter
public class Subject extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Column(nullable = false)
    private int fee;

    @Column(nullable = false,length = 30)
    private String subject_name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject_Field subject_field;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Subject_Stat subject_stat;

    @Column(length = 30)
    private String lecture_time;

    @Column(length = 10)
    private String period;

    private int personnel;

    //자격증/취창업 ->  자격증취창업으로 변경
    @Enumerated(EnumType.STRING)
    private Subject_Type subject_type;

    @OneToMany(mappedBy = "subject")
    private List<Member_Subject> member_subjects = new ArrayList<>();

    public Subject() {
    }

    public Subject(Member member, int fee, String subject_name, Subject_Field subject_field, Subject_Stat subject_stat, String lecture_time, String period, int personnel, Subject_Type subject_type) {
        this.member = member;
        this.fee = fee;
        this.subject_name = subject_name;
        this.subject_field = subject_field;
        this.subject_stat = subject_stat;
        this.lecture_time = lecture_time;
        this.period = period;
        this.personnel = personnel;
        this.subject_type = subject_type;
    }
}
