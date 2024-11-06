package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.BoardDataJpaRepository;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    BoardDataJpaRepository boardDataJpaRepository;

    //메인 페이지 표시용 공지사항
//    public List<BoardListDto> SelectNotice6(){
//        List<BoardListDto> result = new ArrayList<>();
//        String sql = "SELECT board_code, title, created_date FROM calvin_board WHERE board_type = '공지사항' ORDER BY  board_code DESC LIMIT 6";
//
//
//        result = jdbcTemplate.query(sql, new RowMapper<BoardView>() {
//            @Override
//            public BoardView mapRow(ResultSet rs, int rowNum) throws SQLException {
//                BoardView boardView= new BoardView();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                boardView.setBoard_code(rs.getInt("board_code"));
//                String title = rs.getString("title");
//                if(title.length()>=35){
//                    title = title.substring(0,36) + "...";
//                }
//                boardView.setTitle(title);
//                boardView.setCreated_date(sdf.format(rs.getTimestamp("created_date")));
//                return boardView;
//            }
//        });
//        return  result;
//    }

    public Page<BoardListDto> findAll(Pageable pageable){
        Page<BoardListDto> boardListDtos = Page.empty();
        Page<Board> boardPage;
        boardPage = boardDataJpaRepository.findAll(pageable);
        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(),board.getMember().getId(),board.getTitle(),board.getCreatedDate(),board.getMember().getName(),board.getBoardType(),board.getFiles().isEmpty()?"-1":board.getFiles().get(0).getSave_name()));

        return boardListDtos;
    }

    public Page<BoardListDto> findAllByBoard_type(Board_Type board_type, Pageable pageable){
        Page<BoardListDto> boardListDtos = Page.empty();
        Page<Board> boardPage = boardDataJpaRepository.findAllByBoardType(board_type, pageable);
        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(),board.getMember().getId(),board.getTitle(),board.getCreatedDate(),board.getMember().getName(),board.getBoardType(),board.getFiles().isEmpty()?"-1":board.getFiles().get(0).getSave_name()));
        return boardListDtos;
    }

    public Page<BoardListDto> findAllByTitle(String title, Pageable pageable) {
        Page<BoardListDto> boardListDtos = Page.empty();
        Page<Board> boardPage = boardDataJpaRepository.findAllByTitleContaining(title, pageable);
        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(),board.getMember().getId(),board.getTitle(),board.getCreatedDate(),board.getMember().getName(),board.getBoardType(),board.getFiles().isEmpty()?"-1":board.getFiles().get(0).getSave_name()));
        return boardListDtos;
    }

    public Page<BoardListDto> findAllByTitleAndBoard_type(String title,Board_Type board_type, Pageable pageable) {
        Page<BoardListDto> boardListDtos = Page.empty();
        Page<Board>  boardPage = boardDataJpaRepository.findAllByBoardTypeAndTitleContaining(board_type,title,pageable);
        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(),board.getMember().getId(),board.getTitle(),board.getCreatedDate(),board.getMember().getName(),board.getBoardType(),board.getFiles().isEmpty()?"-1":board.getFiles().get(0).getSave_name()));
        return boardListDtos;
    }
}
