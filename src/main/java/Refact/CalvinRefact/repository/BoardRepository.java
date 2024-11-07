package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.*;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.dto.board.BoardDetailDto;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static Refact.CalvinRefact.entity.QBoard.board;
import static Refact.CalvinRefact.entity.QFile.file;
import static Refact.CalvinRefact.entity.QMember.member;

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
                board.boardType
                ))
                .from(board)
                .where(board.boardType.eq(board_type))
                .fetch();
    }

    //게시글 디테일 쿼리
    public BoardDetailDto findBoardDetailById(Long id) {
        BoardDetailDto boardDetailDto = queryFactory.select(Projections.fields(BoardDetailDto.class,
                        board.id.as("board_id"),
                        member.name,
                        board.title,
                        board.contents,
                        board.boardType
                ))
                .from(board)
                .join(board.member, member)
                .where(board.id.eq(id))
                .fetchOne();

        List<FileSimpleDto> files = queryFactory.select(Projections.fields(FileSimpleDto.class,
                file.id,
                file.original_name,
                file.save_name,
                file.size
                ))
                .from(file)
                .join(file.board,board)
                .where(board.id.eq(id))
                .fetch();

        boardDetailDto.setFiles(files);

        return boardDetailDto;
    }

}
