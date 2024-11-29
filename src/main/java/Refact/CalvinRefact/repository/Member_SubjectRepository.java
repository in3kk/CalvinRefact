package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.QMember;
import Refact.CalvinRefact.entity.QMember_Subject;
import Refact.CalvinRefact.entity.QSubject;
import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import Refact.CalvinRefact.repository.dto.Member_Subject.ApplyListDto;
import Refact.CalvinRefact.repository.dto.member.MemberSubjectListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static Refact.CalvinRefact.entity.QMember.member;
import static Refact.CalvinRefact.entity.QMember_Subject.member_Subject;
import static Refact.CalvinRefact.entity.QSubject.subject;

@Repository
public class Member_SubjectRepository {

    @Autowired
    Member_SubjectDataJpaRepository member_subjectDataJpaRepository;
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
                subject.subject_stat,
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

    //신청여부 확인
    public boolean findApplyExistById(String email, Long subjectId) {
        return queryFactory.selectOne()
                .from(member_Subject)
                .join(member_Subject.member,member)
                .join(member_Subject.subject,subject)
                .where(member_Subject.member.email.eq(email).and(member_Subject.subject.id.eq(subjectId)))
                .fetchOne() == null;
    }

    public Page<ApplyListDto> findApplyList(Pageable pageable) {
        List<ApplyListDto> applyListDtos = queryFactory.select(Projections.fields(ApplyListDto.class,
                        member_Subject.id.as("apply_code"),
                        member_Subject.pay_stat,
                        member.name,
                        member.email,
                        subject.subject_name,
                        subject.subject_field
                ))
                .from(member_Subject)
                .join(member_Subject.member, member)
                .join(member_Subject.subject, subject)
                .orderBy(member_Subject.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Member_Subject> countQuery = queryFactory.select(member_Subject)
                        .from(member_Subject);
        return PageableExecutionUtils.getPage(applyListDtos,pageable,()->countQuery.fetch().size());
    }

    public Page<ApplyListDto> findApplyListBySubject_name(Pageable pageable, String subject_name) {
        List<ApplyListDto> applyListDtos = queryFactory.select(Projections.fields(ApplyListDto.class,
                        member_Subject.id.as("apply_code"),
                        member_Subject.pay_stat,
                        member.name,
                        member.email,
                        subject.subject_name,
                        subject.subject_field
                ))
                .from(member_Subject)
                .join(member_Subject.member,member)
                .join(member_Subject.subject,subject)
                .where(subject.subject_name.contains(subject_name))
                .orderBy(member_Subject.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Member_Subject> countQuery = queryFactory.select(member_Subject)
                .from(member_Subject);
        return PageableExecutionUtils.getPage(applyListDtos, pageable, () -> countQuery.fetch().size());
    }

    public Page<ApplyListDto> findApplyListByEmail(Pageable pageable, String search_word) {
        List<ApplyListDto> applyListDtos = queryFactory.select(Projections.fields(ApplyListDto.class,
                        member_Subject.id.as("apply_code"),
                        member_Subject.pay_stat,
                        member.name,
                        member.email,
                        subject.subject_name,
                        subject.subject_field
                ))
                .from(member_Subject)
                .join(member_Subject.member,member)
                .join(member_Subject.subject,subject)
                .where(member.email.contains(search_word))
                .orderBy(member_Subject.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Member_Subject> countQuery = queryFactory.select(member_Subject)
                .from(member_Subject);
        return PageableExecutionUtils.getPage(applyListDtos, pageable, () -> countQuery.fetch().size());
    }


    public boolean updatePay_stat(Long apply_id, Pay_Stat pay_stat) {
        boolean result = false;
        Optional<Member_Subject> member_subjectOptional = member_subjectDataJpaRepository.findById(apply_id);
        if (member_subjectOptional.isPresent()) {
            Member_Subject member_subject = member_subjectOptional.get();

            member_subjectDataJpaRepository.save(new Member_Subject(member_subject.getMember(),member_subject.getSubject(),member_subject.getApp_date(),pay_stat));
            result = true;
        }
        return result;
    }
}
