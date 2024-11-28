package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.*;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.dto.board.BoardDetailDto;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

import static Refact.CalvinRefact.entity.QBoard.board;
import static Refact.CalvinRefact.entity.QFile.file;
import static Refact.CalvinRefact.entity.QMember.member;
import static Refact.CalvinRefact.entity.QMember_Subject.member_Subject;

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
                        board.boardType,
                        board.createdDate.as("created_date")
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

    public Page<BoardListDto> findAll(Pageable pageable) {
        QFile fileSub = new QFile("fileSub");
        List<BoardListDto> result = queryFactory.select(Projections.fields(BoardListDto.class,
                        board.id.as("board_code"),
                        member.id.as("member_code"),
                        board.title,
                        board.createdDate.as("created_date"),
                        member.name,
                        board.boardType.as("board_type")
                        ))
                .from(board)
                .join(board.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        for (BoardListDto boardListDto : result) {
            List<FileSimpleDto> Files = queryFactory.select(Projections.fields(FileSimpleDto.class,
                            file.save_name
                    ))
                    .from(file)
                    .where(file.board.id.eq(boardListDto.getBoard_code()))
                    .fetch();
            if (Files.isEmpty()) {
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\white.png");
            }else{
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\"+Files.get(0).getSave_name());
            }
        }
        JPAQuery<Board> countQuery = queryFactory.select(board)
                .from(board);
        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetch().size());

    }

    public Page<BoardListDto> findAllByBoard_Type(Board_Type boardType, Pageable pageable) {
        QFile fileSub = new QFile("fileSub");
        List<BoardListDto> result = queryFactory.select(Projections.fields(BoardListDto.class,
                        board.id.as("board_code"),
                        member.id.as("member_code"),
                        board.title,
                        board.createdDate.as("created_date"),
                        member.name,
                        board.boardType.as("board_type")
                ))
                .from(board)
                .join(board.member, member)
                .where(board.boardType.eq(boardType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        for (BoardListDto boardListDto : result) {
            List<FileSimpleDto> Files = queryFactory.select(Projections.fields(FileSimpleDto.class,
                            file.save_name
                    ))
                    .from(file)
                    .where(file.board.id.eq(boardListDto.getBoard_code()))
                    .fetch();
            if (Files.isEmpty()) {
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\white.png");
            }else{
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\"+Files.get(0).getSave_name());
            }
        }
        JPAQuery<Board> countQuery = queryFactory.select(board)
                .from(board).where(board.boardType.eq(boardType));
        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetch().size());
    }

    public Page<BoardListDto> findAllByTitle(String title, Pageable pageable) {
        QFile fileSub = new QFile("fileSub");
        List<BoardListDto> result = queryFactory.select(Projections.fields(BoardListDto.class,
                        board.id.as("board_code"),
                        member.id.as("member_code"),
                        board.title,
                        board.createdDate.as("created_date"),
                        member.name,
                        board.boardType.as("board_type")
                ))
                .from(board)
                .join(board.member, member)
                .where(board.title.like(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        for (BoardListDto boardListDto : result) {
            List<FileSimpleDto> Files = queryFactory.select(Projections.fields(FileSimpleDto.class,
                            file.save_name
                    ))
                    .from(file)
                    .where(file.board.id.eq(boardListDto.getBoard_code()))
                    .fetch();
            if (Files.isEmpty()) {
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\white.png");
            }else{
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\"+Files.get(0).getSave_name());
            }
        }
        JPAQuery<Board> countQuery = queryFactory.select(board)
                .from(board).where(board.title.like(title));
        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetch().size());
    }

    public Page<BoardListDto> findAllByBoard_TypeAndTitle(Board_Type boardType, String title, Pageable pageable) {
        QFile fileSub = new QFile("fileSub");
        List<BoardListDto> result = queryFactory.select(Projections.fields(BoardListDto.class,
                        board.id.as("board_code"),
                        member.id.as("member_code"),
                        board.title,
                        board.createdDate.as("created_date"),
                        member.name,
                        board.boardType.as("board_type")
                ))
                .from(board)
                .join(board.member, member)
                .where(board.boardType.eq(boardType).and(board.title.like(title)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        for (BoardListDto boardListDto : result) {
            List<FileSimpleDto> Files = queryFactory.select(Projections.fields(FileSimpleDto.class,
                            file.save_name
                    ))
                    .from(file)
                    .where(file.board.id.eq(boardListDto.getBoard_code()))
                    .fetch();
            if (Files.isEmpty()) {
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\white.png");
            }else{
                boardListDto.setBoard_thumbnail("F:\\CalvinUploadFiles\\"+Files.get(0).getSave_name());
            }
        }
        JPAQuery<Board> countQuery = queryFactory.select(board)
                .from(board).where(board.boardType.eq(boardType).and(board.title.like(title)));
        return PageableExecutionUtils.getPage(result, pageable, () -> countQuery.fetch().size());
    }
}
