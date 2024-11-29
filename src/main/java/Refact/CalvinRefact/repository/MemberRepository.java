package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.QMember;
import Refact.CalvinRefact.repository.dto.member.MemberListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Refact.CalvinRefact.entity.QBoard.board;
import static Refact.CalvinRefact.entity.QMember.member;

@Repository
public class MemberRepository {
    EntityManager em;
    JPAQueryFactory queryFactory;
    public MemberRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }
    public Page<MemberListDto> findAll(Pageable pageable) {
        List<MemberListDto> result = queryFactory.select(Projections.fields(MemberListDto.class,
                        member.id.as("id"),
                        member.email,
                        member.name,
                        member.phone_number,
                        member.memberType.as("member_type")
                ))
                .from(member)
                .fetch();
        JPAQuery<Member> countQuery = queryFactory.select(member)
                .from(member);
        return PageableExecutionUtils.getPage(result,pageable,()->countQuery.fetch().size());
    }

    public Page<MemberListDto> findAllByEmail(String email,Pageable pageable) {

        List<MemberListDto> result = queryFactory.select(Projections.fields(MemberListDto.class,
                        member.id.as("id"),
                        member.email,
                        member.name,
                        member.phone_number,
                        member.memberType.as("member_type")
                ))
                .from(member)
                .where(member.email.contains(email))
                .fetch();
        JPAQuery<Member> countQuery = queryFactory.select(member)
                .from(member).where(member.email.contains(email));
        return PageableExecutionUtils.getPage(result,pageable,()->countQuery.fetch().size());
    }

    public Page<MemberListDto> findAllByUsername(String name, Pageable pageable) {
        List<MemberListDto> result = queryFactory.select(Projections.fields(MemberListDto.class,
                        member.id.as("id"),
                        member.email,
                        member.name,
                        member.phone_number,
                        member.memberType.as("member_type")
                ))
                .from(member)
                .where(member.name.contains(name))
                .fetch();
        JPAQuery<Member> countQuery = queryFactory.select(member)
                .from(member).where(member.name.contains(name));
        return PageableExecutionUtils.getPage(result,pageable,()->countQuery.fetch().size());
    }
}
