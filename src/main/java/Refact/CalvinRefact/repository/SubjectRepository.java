package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.QMember;
import Refact.CalvinRefact.entity.QSubject;
import Refact.CalvinRefact.entity.Subject;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.dto.subject.SubjectDetailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Refact.CalvinRefact.entity.QMember.member;
import static Refact.CalvinRefact.entity.QSubject.subject;

@Repository
public class SubjectRepository {

    EntityManager em;
    JPAQueryFactory queryFactory;
    public SubjectRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }
    //강의 리스트 type
    public List<SubjectListDto> findByType(Subject_Type subjectType) {
        return queryFactory.select(Projections.fields(SubjectListDto.class,
                subject.id,
                subject.fee,
                subject.subject_name,
                subject.subject_field,
                subject.subject_stat,
                subject.lecture_time,
                subject.period,
                subject.personnel
                ))
                .from(subject)
                .where(subject.subject_type.eq(subjectType))
                .fetch();
    }
    //강의 리스트 type and field
    public List<SubjectListDto> findByTypeAndField(Subject_Type subjectType, Subject_Field subject_field) {
        return queryFactory.select(Projections.fields(SubjectListDto.class,
                        subject.id,
                        subject.fee,
                        subject.subject_name,
                        subject.subject_field,
                        subject.subject_stat,
                        subject.lecture_time,
                        subject.period,
                        subject.personnel
                ))
                .from(subject)
                .where(subject.subject_type.eq(subjectType).and(subject.subject_field.eq(subject_field)))
                .fetch();
    }

    //강의 리스트 type and filed and name
    public List<SubjectListDto> findByTypeAndFieldAndName(Subject_Type subjectType, Subject_Field subjectField, String name) {
        return queryFactory.select(Projections.fields(SubjectListDto.class,
                        subject.id,
                        subject.fee,
                        subject.subject_name,
                        subject.subject_field,
                        subject.subject_stat,
                        subject.lecture_time,
                        subject.period,
                        subject.personnel
                ))
                .from(subject)
                .where(subject.subject_type.eq(subjectType).and(subject.subject_field.eq(subjectField).and(subject.subject_name.contains(name))))
                .fetch();
    }

    //강의 디테일 id
    public SubjectDetailDto findSubjectDetailById(Long id) {
        return queryFactory.select(Projections.fields(SubjectDetailDto.class,
                        subject.id.as("subject_code"),
                        subject.subject_name,
                        subject.subject_field,
                        subject.subject_type,
                        subject.subject_stat,
                        subject.lecture_time,
                        subject.fee,
                        subject.period,
                        subject.personnel,
                        member.name.as("member_name"),
                        member.id
                ))
                .from(subject)
                .join(subject.member, member)
                .fetchOne();
    }

    public Page<SubjectListDto> findAllSubjectList(Pageable pageable) {
        List<SubjectListDto> queryResult = queryFactory.select(Projections.fields(SubjectListDto.class,
                        subject.id,
                        subject.fee,
                        subject.subject_name,
                        subject.subject_type,
                        subject.subject_field,
                        subject.subject_stat
                ))
                .from(subject)
                .orderBy(subject.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Subject> countQuery = queryFactory.select(subject)
                .from(subject);
        return PageableExecutionUtils.getPage(queryResult, pageable, () -> countQuery.fetch().size());
    }

    public Page<SubjectListDto> findSubjectListByName(Pageable pageable, String search_word) {
        List<SubjectListDto> queryResult = queryFactory.select(Projections.fields(SubjectListDto.class,
                        subject.id,
                        subject.fee,
                        subject.subject_name,
                        subject.subject_type,
                        subject.subject_field,
                        subject.subject_stat
                ))
                .from(subject)
                .where(subject.subject_name.like(search_word))
                .orderBy(subject.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Subject> countQuery = queryFactory.select(subject)
                .from(subject)
                .where(subject.subject_name.like(search_word));

        return PageableExecutionUtils.getPage(queryResult,pageable,() -> countQuery.fetch().size());
    }

    public Page<SubjectListDto> findSubjectListByField(Pageable pageable, String search_word) {
        List<SubjectListDto> queryResult = queryFactory.select(Projections.fields(SubjectListDto.class,
                        subject.id,
                        subject.fee,
                        subject.subject_name,
                        subject.subject_type,
                        subject.subject_field,
                        subject.subject_stat
                ))
                .from(subject)
                .where(subject.subject_field.eq(Subject_Field.valueOf(search_word)))
                .orderBy(subject.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Subject> countQuery = queryFactory.select(subject)
                .from(subject)
                .where(subject.subject_field.eq(Subject_Field.valueOf(search_word)));

        return PageableExecutionUtils.getPage(queryResult,pageable,() -> countQuery.fetch().size());
    }

    public Page<SubjectListDto> findSubjectListByType(Pageable pageable, String search_word) {
        List<SubjectListDto> queryResult = queryFactory.select(Projections.fields(SubjectListDto.class,
                        subject.id,
                        subject.fee,
                        subject.subject_name,
                        subject.subject_type,
                        subject.subject_field,
                        subject.subject_stat
                ))
                .from(subject)
                .where(subject.subject_type.eq(Subject_Type.valueOf(search_word)))
                .orderBy(subject.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Subject> countQuery = queryFactory.select(subject)
                .from(subject)
                .where(subject.subject_type.eq(Subject_Type.valueOf(search_word)));

        return PageableExecutionUtils.getPage(queryResult,pageable,() -> countQuery.fetch().size());
    }
}
