package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.QMember;
import Refact.CalvinRefact.entity.QMember_Subject;
import Refact.CalvinRefact.entity.QSubject;
import Refact.CalvinRefact.repository.dto.member.MemberSubjectListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Refact.CalvinRefact.entity.QMember.member;
import static Refact.CalvinRefact.entity.QMember_Subject.member_Subject;
import static Refact.CalvinRefact.entity.QSubject.subject;

@Repository
public class Member_SubjectRepository {

    EntityManager em;
    JPAQueryFactory queryFactory;
    public Member_SubjectRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    //내 강의 리스트
    public List<MemberSubjectListDto> findMySubjectByEmail(String email, Pageable pageable){
        return queryFactory.select(Projections.fields(MemberSubjectListDto.class,
                subject.id.as("subject_id"),
                subject.subject_name,
                subject.subject_field,
                subject.fee,
                member_Subject.id.as("member_subject_id"),
                member_Subject.pay_stat,
                member.id.as("member_id")
                ))
                .from(member_Subject)
                .join(member_Subject.subject,subject)
                .join(member_Subject.member,member)
                .where(member.email.eq(email))
                .orderBy(member_Subject.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
