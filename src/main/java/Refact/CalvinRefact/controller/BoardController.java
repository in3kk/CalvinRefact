package Refact.CalvinRefact.controller;

import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.exception.InvalidPermissionException;
import Refact.CalvinRefact.repository.BoardRepository;
import Refact.CalvinRefact.repository.dto.board.BoardDetailDto;
import Refact.CalvinRefact.repository.dto.board.BoardListDto;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import Refact.CalvinRefact.service.BoardService;
import Refact.CalvinRefact.service.CalvinService;
import Refact.CalvinRefact.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class BoardController {

    @Autowired
    CalvinService calvinService;
    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;


    //게시판 페이지 search_type = 검색방법 ex) 제목, 내용 admin => 관리자용 페이지
    @GetMapping({"/menu/board","/mypage/admin/board"})
    public String BoardPage(@RequestParam(value = "search_word", required = false, defaultValue = "") String search_word,
                            @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
                            @RequestParam(value = "board_type",required = false, defaultValue = "") String board_type,
                            @PageableDefault(size = 20,sort = {"board_id"}) Pageable pageable,
                            HttpSession httpSession, Model model, RedirectAttributes redirectAttributes){
        if(!search_word.equals("")){
            search_word = calvinService.searchWordFilter(search_word);
        }
        int count = 0;
        Page<BoardListDto> board_list = Page.empty();
        String result = "";
        String page_type = "8.5";
        try {
            if (search_word.equals("")) {
                if (board_type.equals("")) {
                    memberService.permissionCheck(httpSession.getAttribute("member_id").toString());
                    if (httpSession.getAttribute("member_type").equals("dd") || httpSession.getAttribute("member_type").equals("st") || httpSession.getAttribute("member_type").equals("ai")) {
                        board_list = boardService.findAll(pageable);
                        result = "menu/mypage/admin_board";
                        page_type = "9.3";
                    }
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
                    memberService.permissionCheck(httpSession.getAttribute("member_id").toString());
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
            int page = pageable.getPageNumber();
            if (page % 10 == 0) {
                begin_page = page - 9;
            } else {
                begin_page = page / 10 * 10 + 1;
            }

            int max_page = board_list.getTotalPages();
            model.addAttribute("search_word", search_word);
            model.addAttribute("search_type", search_type);
            model.addAttribute("page", page);
            model.addAttribute("begin_page", begin_page);
            model.addAttribute("max_page", max_page);
            model.addAttribute("board_list", board_list);
            model.addAttribute("page_type", page_type);
            model.addAttribute("board_type", board_type);

        } catch (InvalidPermissionException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            result = "redirect:/";
        }
        return result;
    }

    //게시글 확인 페이지 id == board_code
    @GetMapping("/menu/board/view")
    public String BoardView(@RequestParam(value = "id") Long id, Model model){
        BoardDetailDto boardDetailDto = boardService.findBoardDetailById(id);

        model.addAttribute("boardView", boardDetailDto);
        model.addAttribute("file1",boardDetailDto.getFiles().get(0));
        model.addAttribute("file2",boardDetailDto.getFiles().get(1));
        model.addAttribute("file3",boardDetailDto.getFiles().get(2));
        model.addAttribute("file4",boardDetailDto.getFiles().get(3));
        model.addAttribute("file5",boardDetailDto.getFiles().get(4));
        model.addAttribute("page_type", "8.5");
        Board_Type board_type = boardDetailDto.getBoardType();
        if(board_type.equals(Board_Type.공지사항)){
            model.addAttribute("page_type", "8.5");
        }else if(board_type.equals(Board_Type.사진자료실)){
            model.addAttribute("page_type", "8.6");
        }else if(board_type.equals(Board_Type.서식자료실)){
            model.addAttribute("page_type", "8.7");
        }
        return "menu/board/board_view";
    }
    //게시글 작성 페이지
    @GetMapping("/menu/board/write")
    public String BoardWrite(HttpSession httpSession){
        String result ="<script>window.location.href='/';</script>";
        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
            try {
                memberService.permissionCheck(httpSession.getAttribute("id").toString());
                result = "<script>window.location.href='/menu/board/board_write';</script>";
            } catch (InvalidPermissionException e) {
                result = "<script>alert('"+e.getMessage()+"');history.back();;</script>";
            }
        }else {
            result="<script>alert('로그인이 필요한 서비스 입니다.');window.location.href='/member/login';</script>";
        }
        return result;
    }

    //게시글 작성
    @PostMapping("/menu/board/write")
    @ResponseBody
    public String InsertBoard(@RequestParam(value = "title") String title, @RequestParam(value = "contents") String board_contents,
                              @RequestParam(value = "member_id") String member_id,
                              @RequestParam(value= "board_type") Board_Type board_type,
                              @RequestParam(value = "file1", required = false) MultipartFile file1,
                              @RequestParam(value = "file2", required = false) MultipartFile file2,
                              @RequestParam(value = "file3", required = false) MultipartFile file3,
                              @RequestParam(value = "file4", required = false) MultipartFile file4,
                              @RequestParam(value = "file5", required = false) MultipartFile file5){
        String result = "";
        List<MultipartFile> file_list = new ArrayList<>();
        boolean token = false;
        if(file1 != null){
            file_list.add(file1);
        }
        if(file2 != null){
            file_list.add(file2);
        }
        if(file3 != null){
            file_list.add(file3);
        }
        if(file4 != null){
            file_list.add(file4);
        }
        if(file5 != null){
            file_list.add(file5);
        }

        try {
            boardService.saveBoard(member_id, title, board_contents, board_type, file_list);
            result= "window.location.href='/menu/board';</script>";
        } catch (Exception e) {
            result="<script>alert('게시글 작성에 실패했습니다.');window.location.href='/menu/board';</script>";
        }

        return result;
    }

    //게시글 삭제
    @GetMapping("/menu/board/delete")
    @ResponseBody
    public String boardDelete(@RequestParam(value = "board_code") Long board_code, HttpSession httpSession, Model model){
        String result="";
        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
            result="<script>alert('로그인이 필요한 서비스 입니다.');window.location.href='/member/login';</script>";
        }else{
            try {
                boardService.deleteBoard(board_code);
                result = "<script>alert('게시글이 삭제되었습니다.');history.back();</script>";
            } catch (InvalidPermissionException e) {
                result = "<script>alert('"+e.getMessage()+"');window.location.href='/';</script>";
            } catch (Exception e) {
                result = "<script>alert('게시글 삭제에 실패했습니다.');history.back();</script>";
            }
        }
        return result;
    }
}
