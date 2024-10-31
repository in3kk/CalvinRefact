package Refact.CalvinRefact.service;

import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

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
}
