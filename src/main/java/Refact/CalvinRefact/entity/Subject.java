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
 */
@Entity @Getter
public class Subject extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int fee;
    private String subject_name;

    @Enumerated(EnumType.STRING)
    private Subject_Field subject_field;

    @Enumerated(EnumType.ORDINAL)
    private Subject_Stat subject_stat;

    private String lecture_time;

    private String period;

    private int personnel;

    //자격증/취창업 ->  자격증취창업으로 변경
    private Subject_Type subject_type;

    @OneToMany(mappedBy = "subject")
    private List<Member_Subject> member_subjects = new ArrayList<>();
}
