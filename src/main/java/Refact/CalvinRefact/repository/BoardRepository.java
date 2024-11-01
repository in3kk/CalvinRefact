package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.QBoard;
import Refact.CalvinRefact.entity.QMember;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Refact.CalvinRefact.entity.QBoard.board;

@Repository
public class BoardRepository {
    EntityManager em;
    JPAQueryFactory queryFactory;
    public BoardRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }

    //메인 페이지 표시용 공지사항
    public List<BoardListDto> findTop6ByBoard_type(Board_Type board_type){
        return queryFactory.select(Projections.fields(BoardListDto.class,
                board.id.as("board_code"),
                board.member.id.as("member_code"),
                board.title,
                board.createdDate,
                board.member.name,
                board.board_type
                ))
                .from(board)
                .where(board.board_type.eq(board_type))
                .fetch();

    }
}
