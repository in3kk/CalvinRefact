package Refact.CalvinRefact.entity;

import Refact.CalvinRefact.entity.baseEntity.BaseEntity;
import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Member_Subject extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private LocalDate app_date;

    @Enumerated(EnumType.STRING)
    private Pay_Stat pay_stat;

    public Member_Subject() {
    }

    public Member_Subject(Member member, Subject subject, LocalDate app_date, Pay_Stat pay_stat) {
        this.member = member;
        this.subject = subject;
        this.app_date = app_date;
        this.pay_stat = pay_stat;
    }
}