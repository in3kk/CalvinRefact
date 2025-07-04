package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Board;
import Refact.CalvinRefact.entity.File;
import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.exception.NotExistBoardException;
import Refact.CalvinRefact.repository.BoardDataJpaRepository;
import Refact.CalvinRefact.repository.BoardRepository;
import Refact.CalvinRefact.repository.MemberDataJpaRepository;
import Refact.CalvinRefact.repository.dto.board.BoardDetailDto;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BoardService {
    @Autowired
    BoardDataJpaRepository boardDataJpaRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberDataJpaRepository memberDataJpaRepository;
    @Autowired
    FileService fileService;
    @Autowired
    CalvinService calvinService;
    @Autowired
    EntityManager em;

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

    public Page<BoardListDto> findAll(Pageable pageable) {
        Page<BoardListDto> boardListDtos = boardRepository.findAll(pageable);
//        Page<Board> boardPage;
//        boardPage = boardDataJpaRepository.findAll(pageable);
//        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(), board.getMember().getId(), board.getTitle(), board.getCreatedDate(), board.getMember().getName(), board.getBoardType(), board.getFiles().isEmpty() ? "-1" : board.getFiles().get(0).getSave_name()));

        return boardListDtos;
    }

    public Page<BoardListDto> findAllByBoard_type(Board_Type board_type, Pageable pageable) {
        LocalDateTime startTime = LocalDateTime.now();
        Page<BoardListDto> boardListDtos = boardRepository.findAllByBoard_Type(board_type,pageable);
//        Page<Board> boardPage = boardDataJpaRepository.findAllByBoardType(board_type, pageable);
//        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(), board.getMember().getId(), board.getTitle(), board.getCreatedDate(), board.getMember().getName(), board.getBoardType(), board.getFiles().isEmpty() ? "-1" : board.getFiles().get(0).getSave_name()));
        LocalDateTime now = LocalDateTime.now();
        System.out.println("소요시간 : "+ ChronoUnit.MILLIS.between(startTime,now)+"밀리초");
        System.out.println("소요시간 : "+ ChronoUnit.NANOS.between(startTime,now)+"나노초");
        return boardListDtos;
    }

    public Page<BoardListDto> findAllByTitle(String title, Pageable pageable) {
        Page<BoardListDto> boardListDtos = boardRepository.findAllByTitle(title,pageable);
//        Page<Board> boardPage = boardDataJpaRepository.findAllByTitleContaining(title, pageable);
//        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(), board.getMember().getId(), board.getTitle(), board.getCreatedDate(), board.getMember().getName(), board.getBoardType(), board.getFiles().isEmpty() ? "-1" : board.getFiles().get(0).getSave_name()));
        return boardListDtos;
    }

    public Page<BoardListDto> findAllByTitleAndBoard_type(String title, Board_Type board_type, Pageable pageable) {
        Page<BoardListDto> boardListDtos = boardRepository.findAllByBoard_TypeAndTitle(board_type,title,pageable);
//        Page<Board> boardPage = boardDataJpaRepository.findAllByBoardTypeAndTitleContaining(board_type, title, pageable);
//        boardListDtos = boardPage.map(board -> new BoardListDto(board.getId(), board.getMember().getId(), board.getTitle(), board.getCreatedDate(), board.getMember().getName(), board.getBoardType(), board.getFiles().isEmpty() ? "-1" : board.getFiles().get(0).getSave_name()));
        return boardListDtos;
    }

    //게시글 조회
    public BoardDetailDto findBoardDetailById(Long id) {
        BoardDetailDto boardDetailDto = boardRepository.findBoardDetailById(id);
        for (int x = 0; x < boardDetailDto.getFiles().size(); x++) {
            if (boardDetailDto.getFiles().get(x) == null) {
                boardDetailDto.getFiles().add(new FileSimpleDto("-1"));
            }
        }
        for (int x = boardDetailDto.getFiles().size(); x < 5; x++) {
            boardDetailDto.getFiles().add(new FileSimpleDto("-1"));
        }
        boardDetailDto.setThumbnail(boardDetailDto.getFiles().get(0).getSave_name());

        return boardDetailDto;
    }

    //게시글 작성 Exception
    @Transactional(rollbackFor = {Exception.class})
    public void saveBoard(String email, String title, String contents, Board_Type boardType, List<MultipartFile> files) throws Exception {
//        Pattern p1 = Pattern.compile("<([a-zA-Z]+)(\\s[^>]*)?>(?![\\s\\S]*</\\1>)");
//        Matcher m = p1.matcher(contents);
//        contents = m.replaceAll("");//드래그앤드롭 이미지 입력 방지 코드 -> calvinService.filter 로 변경
        contents = calvinService.filter(contents, "<([a-zA-Z]+)(\\s[^>]*)?>(?![\\s\\S]*</\\1>)");
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            //이미지 입력 추가
            Board board = new Board(member, title, contents, boardType);
            int current_code = 1;
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = fileService.saveFile(board, file);
                    contents = contents.replace("&lt;&lt;&lt;"+current_code+"&gt;&gt;&gt;","<img src=\"/imgPath/"+fileName+"\" width=\"100%\" height=\"auto\"/>");
                }
                current_code++;
            }
            board.setContents(contents);
            boardDataJpaRepository.save(board);
        }
    }
    //게시글 삭제 Exception
    @Transactional(rollbackFor = {Exception.class})
    public void deleteBoard(Long id) throws Exception{
        Optional<Board> boardOptional = boardDataJpaRepository.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            List<File> files = board.getFiles();
            for (File file : files) {
                fileService.deleteFile(file);
            }
            boardDataJpaRepository.deleteById(id);
        } else {
            throw new NotExistBoardException("존재하지 않는 게시글입니다.");
        }
    }

    //tiptap 게시글 에디터 삭제 Exception
    @Transactional(rollbackFor = {Exception.class})
    public void deleteBoardTiptap(Long id) throws Exception{
        Optional<Board> boardOptional = boardDataJpaRepository.findById(id);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            List<File> files = board.getFiles();
            for (File file : files) {
                fileService.deleteFile(file);
            }
            String contents= board.getContents();
            //로컬
            Pattern urlPattern = Pattern.compile("(?<=(F:\\\\CalvinUploadFiles\\\\))[^\"]+");
            //서버
//            Pattern urlPattern = Pattern.compile("(?<=(/iceadmin/CalvinUploadFile/))[^\"]+");
            Matcher m = urlPattern.matcher(contents);

            while (m.find()) {
                String target = m.group();
                fileService.deleteFileByFileName(target);
            }
            boardDataJpaRepository.deleteById(id);
        } else {
            throw new NotExistBoardException("존재하지 않는 게시글입니다.");
        }
    }
    //tiptap 에디터 게시글 작성
    @Transactional(rollbackFor = {Exception.class})
    public void saveBoardTiptap(String email, String title, Map<String, Object> contentJson, Board_Type boardType, List<MultipartFile> files) throws Exception {
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        //Json -> String 형식으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String contents = objectMapper.writeValueAsString(contentJson);

        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            Board board = new Board(member, title, contents, boardType);
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = fileService.saveFile(board, file);
                }
            }
            board.setContents(contents);
            boardDataJpaRepository.save(board);
        }
    }

    //tiptap 에디터 게시글 조회
    public BoardDetailDto findBoardDetailByIdTiptap(Long id) {
        BoardDetailDto boardDetailDto = boardRepository.findBoardDetailById(id);
        for (int x = 0; x < boardDetailDto.getFiles().size(); x++) {
            if (boardDetailDto.getFiles().get(x) == null) {
                boardDetailDto.getFiles().add(new FileSimpleDto("-1"));
            }
        }
        for (int x = boardDetailDto.getFiles().size(); x < 5; x++) {
            boardDetailDto.getFiles().add(new FileSimpleDto("-1"));
        }
        boardDetailDto.setThumbnail(boardDetailDto.getFiles().get(0).getSave_name());

        return boardDetailDto;
    }
}
