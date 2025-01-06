package Refact.CalvinRefact.controller.api;

import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.exception.InvalidPermissionException;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import Refact.CalvinRefact.service.BoardService;
import Refact.CalvinRefact.service.CalvinService;
import Refact.CalvinRefact.service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectApiController {

    @Autowired
    SubjectService subjectService;
    @Autowired
    CalvinService calvinService;
    @Autowired
    BoardService boardService;
    @GetMapping("/subjectFieldList")
    @ResponseBody
    public ResponseEntity<List<String>> subjectFiledList(@RequestParam("subject_type") String subjectType) {
        List<String> subjectFieldList = new ArrayList<>();
        if (subjectType.equals(Subject_Type.학점은행제.toString())) {
            subjectFieldList.add("사회복지학과");
            subjectFieldList.add("상담");
            subjectFieldList.add("신학");
            subjectFieldList.add("아동");
            subjectFieldList.add("실용음악과");
            subjectFieldList.add("교양");
            subjectFieldList.add("사회복지현장실습");
        } else if (subjectType.equals(Subject_Type.특별교육과정.toString())) {
            subjectFieldList.add("용인");
            subjectFieldList.add("서현정치경제");
            subjectFieldList.add("경기교육");
            subjectFieldList.add("레이번스축구아카데미");
            subjectFieldList.add("연예");
            subjectFieldList.add("미디어");
        } else if (subjectType.equals(Subject_Type.일반교양.toString())) {
            subjectFieldList.add("논술강좌");
            subjectFieldList.add("자기계발");
            subjectFieldList.add("생활건강");
            subjectFieldList.add("생활교양");
            subjectFieldList.add("생활예술");
        } else if (subjectType.equals(Subject_Type.자격증취창업.toString())) {
            subjectFieldList.add("전문자격증");
            subjectFieldList.add("민간자격증");
            subjectFieldList.add("기술자격증");
            subjectFieldList.add("취창업");
        } else if (subjectType.equals(Subject_Type.언어.toString())) {
            subjectFieldList.add("성경고전어");
            subjectFieldList.add("제2외국어");
            subjectFieldList.add("한국어");
        } else if (subjectType.equals(Subject_Type.목회.toString())) {
            subjectFieldList.add("사모포럼");
            subjectFieldList.add("성경주해");
            subjectFieldList.add("성경강해");
            subjectFieldList.add("목회트렌드");
        }
        return ResponseEntity.ok(subjectFieldList);
    }

    @GetMapping("/applyList/{subject_id}/{type}")
    public Result applyList(@PathVariable("member_id")Long member_id,
                            @PathVariable("type")String type) {
        List<SubjectListDto> subject_list = subjectService.findSubjectList(Subject_Type.valueOf(type));

        return new Result(subject_list);
    }

    @GetMapping("/boardList/{board_type}/{search_word}/{page}/{size}")
    public Result boardList(@PathVariable("board_type") String board_type,
                            @PathVariable("page") int page,
                            @PathVariable("size") int size,
                            @PathVariable("search_word") String search_word) {
        if(!search_word.equals(" ")){
            search_word = calvinService.searchWordFilter(search_word);
        }
        Pageable pageable = PageRequest.of(page,size);
        int count = 0;
        Page<BoardListDto> board_list = Page.empty();
        String result = "";
        String page_type = "8.5";
        try {
            if (search_word.equals(" ")) {
                if (board_type.equals("공지사항")) {
                    board_list = boardService.findAllByBoard_type(Board_Type.공지사항, pageable);
                } else if (board_type.equals("사진자료실")) {
                    board_list = boardService.findAllByBoard_type(Board_Type.사진자료실, pageable);
                } else if (board_type.equals("서식자료실")) {
                    board_list = boardService.findAllByBoard_type(Board_Type.서식자료실, pageable);
                }
            } else {
                if (board_type.equals("공지사항")) {
                    board_list = boardService.findAllByTitleAndBoard_type(search_word, Board_Type.공지사항, pageable);
                } else if (board_type.equals("사진자료실")) {
                    board_list = boardService.findAllByTitleAndBoard_type(search_word, Board_Type.사진자료실, pageable);
                } else if (board_type.equals("서식자료실")) {
                    board_list = boardService.findAllByTitleAndBoard_type(search_word, Board_Type.서식자료실, pageable);
                }
            }
        } catch (InvalidPermissionException e) {
        }
        return new Result<>(board_list);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
