package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.QSubject;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
