package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.repository.dto.member.MemberSubjectListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Member_SubjectRepository {

    EntityManager em;
    JPAQueryFactory queryFactory;
    public Member_SubjectRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

//    public List<MemberSubjectListDto> findMySubjectByEmail(String email){
//        return queryFactory.select(Projections.fields(MemberSubjectListDto.class,
//                ))
//    }
}
