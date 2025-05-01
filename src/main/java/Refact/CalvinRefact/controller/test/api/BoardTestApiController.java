package Refact.CalvinRefact.controller.test.api;

import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BoardTestApiController {

    @Autowired
    BoardService boardService;


    @PostMapping("/test/board/write")
    @ResponseBody
    public ResponseEntity<Boolean> InsertBoard(@RequestParam(value = "title") String title, @RequestParam(value = "contents") String board_contents,
                                               @RequestParam(value = "member_id") String member_id,
                                               @RequestParam(value = "board_type") String board_type,
                                               @RequestParam(value = "file1", required = false) MultipartFile file1,
                                               @RequestParam(value = "file2", required = false) MultipartFile file2,
                                               @RequestParam(value = "file3", required = false) MultipartFile file3,
                                               @RequestParam(value = "file4", required = false) MultipartFile file4,
                                               @RequestParam(value = "file5", required = false) MultipartFile file5) {
        ResponseEntity<Boolean> result;
        List<MultipartFile> file_list = new ArrayList<>();
        boolean token = false;
        file_list.add(file1);
        file_list.add(file2);
        file_list.add(file3);
        file_list.add(file4);
        file_list.add(file5);
        try {
            boardService.saveBoard(member_id, title, board_contents, Board_Type.valueOf(board_type), file_list);
            result = ResponseEntity.ok(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = ResponseEntity.ok(false);
        }
        return result;
    }

    @PostMapping("/test/board/list")
    @ResponseBody
    public ResponseEntity<?> findBoardList(@RequestParam(value = "search_word", required = false, defaultValue = "") String search_word,
                                           @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
                                           @RequestParam(value = "board_type",required = false, defaultValue = "") String board_type,
                                           @PageableDefault(size = 20,sort = {"board_id"}) Pageable pageable){
        Page<BoardListDto> board_list = Page.empty();
        String result = "";
        String page_type = "8.5";
        ResponseEntity<?> testResult;
        try {
            if (search_word.equals("")) {
                if (board_type.equals("")) {
                    board_list = boardService.findAll(pageable);
                    result = "menu/mypage/admin_board";
                    page_type = "9.3";
                } else if (board_type.equals("공지사항")) {
                    board_list = boardService.findAllByBoard_type(Board_Type.공지사항, pageable);
                    result = "menu/board/board01";
                    page_type = "8.5";
                } else if (board_type.equals("사진자료실")) {
                    board_list = boardService.findAllByBoard_type(Board_Type.사진자료실, pageable);
                    result = "menu/info2/gallery";
                    page_type = "8.6";
                } else if (board_type.equals("서식자료실")) {
                    board_list = boardService.findAllByBoard_type(Board_Type.서식자료실, pageable);
                    result = "menu/info2/format";
                    page_type = "8.7";
                }
            } else {
                if (board_type.equals("")) {
                    board_list = boardService.findAllByTitle(search_word, pageable);
                    result = "menu/mypage/admin_board";
                    page_type = "9.3";

                } else if (board_type.equals("공지사항")) {
                    board_list = boardService.findAllByTitleAndBoard_type(search_word, Board_Type.공지사항, pageable);
                    result = "menu/board/board01";
                    page_type = "8.5";
                } else if (board_type.equals("사진자료실")) {
                    board_list = boardService.findAllByTitleAndBoard_type(search_word, Board_Type.사진자료실, pageable);
                    result = "menu/info2/gallery";
                    page_type = "8.6";
                } else if (board_type.equals("서식자료실")) {
                    board_list = boardService.findAllByTitleAndBoard_type(search_word, Board_Type.서식자료실, pageable);
                    result = "menu/info2/format";
                    page_type = "8.7";
                }
            }

            int begin_page;
            int page = pageable.getPageNumber() + 1;
            if (page % 10 == 0) {
                begin_page = page - 9;
            } else {
                begin_page = page / 10 * 10 + 1;
            }
            int max_page = board_list.getTotalPages();
            testResult = ResponseEntity.ok(search_word + "-" + search_type + "-" + page + "-" + begin_page + "-" + max_page + "-" + board_list.getTotalElements());
        } catch (Exception e) {
            testResult = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿼리에 실패했습니다.");
        }
        return testResult;
    }
}
