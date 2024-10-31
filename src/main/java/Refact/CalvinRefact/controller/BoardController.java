package Refact.CalvinRefact.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class BoardController {
//    //게시판 페이지 search_type = 검색방법 ex) 제목, 내용 admin => 관리자용 페이지
//    @GetMapping({"/menu/board","/mypage/admin/board"})
//    public String BoardPage(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                            @RequestParam(value = "search_word", required = false, defaultValue = "") String search_word,
//                            @RequestParam(value = "search_type", required = false, defaultValue = "1") int search_type,
//                            @RequestParam(value = "board_type",required = false, defaultValue = "") String board_type,
//                            HttpSession httpSession, Model model){
//        if(!search_word.equals("")){
//            Pattern RegPattern1 = Pattern.compile("/[^(A-Za-z가-힣0-9\\s.,)]/");
//            Matcher m = RegPattern1.matcher(search_word);
//            search_word = m.replaceAll(" ");
//            search_word = calvinBoardService.WordValidationPro(search_word);
//        }
//        int count = 0;
//        List<BoardView> board_list = new ArrayList<>();
//        String result = "";
//        String page_type = "8.5";
//        if(search_word.equals("")){
//            if(board_type.equals("")){
//                if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
//                    if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
//                        count = calvinBoardService.paging();
//                        board_list = calvinBoardService.SelectAllBoard(page, count);
//                        result = "menu/mypage/admin_board";
//                        page_type = "9.3";
//                    }
//                }else{
//                    throw new CustomException(ErrorCode.INVALID_PERMISSION);
//                }
//
//            }else if(board_type.equals("공지사항")){
//                count = calvinBoardService.paging(board_type);
//                board_list = calvinBoardService.SelectAllBoard(board_type,page, count);
//                result = "menu/board/board01";
//                page_type = "8.5";
//            }else if(board_type.equals("사진자료실")){
//                count = calvinBoardService.paging(board_type);
//                board_list = calvinBoardService.SelectAllBoard(board_type,page, count);
//                result = "menu/info2/gallery";
//                page_type = "8.6";
//            }else if(board_type.equals("서식자료실")){
//                count = calvinBoardService.paging(board_type);
//                board_list = calvinBoardService.SelectAllBoard(board_type,page, count);
//                result = "menu/info2/format";
//                page_type = "8.7";
//            }
//        }else{
//            if(board_type.equals("")){
//                if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")){
//                    count = calvinBoardService.paging(search_type,search_word);
//                    board_list = calvinBoardService.SelectByTitle(search_word,page, count);
//                    result = "menu/mypage/admin_board";
//                    page_type = "9.3";
//                }else{
//                    throw new CustomException(ErrorCode.INVALID_PERMISSION);
//                }
//            }else if(board_type.equals("공지사항")){
//                count = calvinBoardService.paging(board_type,search_type,search_word);
//                board_list = calvinBoardService.SelectByTitle(board_type,search_word,page, count);
//                result = "menu/board/board01";
//                page_type = "8.5";
//            }else if(board_type.equals("사진자료실")){
//                count = calvinBoardService.paging(board_type,search_type,search_word);
//                board_list = calvinBoardService.SelectByTitle(board_type,search_word,page, count);
//                result = "menu/info2/gallery";
//                page_type = "8.6";
//            }else if(board_type.equals("서식자료실")){
//                count = calvinBoardService.paging(board_type);
//                board_list = calvinBoardService.SelectAllBoard(board_type,page, count);
//                result = "menu/info2/format";
//                page_type = "8.7";
//            }
//        }
//        int begin_page;
//
//        if(page % 10 == 0){
//            begin_page = page-9;
//        }else{
//            begin_page = page/10*10+1;
//        }
//
//        int max_page;
//        if(count/20 == 0 && count%20 > 0){
//            max_page = 1;
//        }else if(count/20 > 0 && count%20 > 0){
//            max_page = count/20 + 1;
//        }else{
//            max_page = count/20;
//        }
//        model.addAttribute("search_word", search_word);
//        model.addAttribute("search_type", search_type);
//        model.addAttribute("page", page);
//        model.addAttribute("begin_page",begin_page);
//        model.addAttribute("max_page", max_page);
//        model.addAttribute("board_list",board_list);
//
//        model.addAttribute("page_type",page_type);
//        model.addAttribute("board_type", board_type);
//        return result;
//    }

//    //게시글 확인 페이지 id == board_code
//    @GetMapping("/menu/board/view")
//    public String BoardView(@RequestParam(value = "id") int id, Model model){
//        BoardView boardView = calvinBoardService.SelectBoardDetail(id);
//        Calvin_file calvinFile1 = calvinFileService.getFileOriginName(boardView.getFile_code1());
//        Calvin_file calvinFile2 = calvinFileService.getFileOriginName(boardView.getFile_code2());
//        Calvin_file calvinFile3 = calvinFileService.getFileOriginName(boardView.getFile_code3());
//        Calvin_file calvinFile4 = calvinFileService.getFileOriginName(boardView.getFile_code4());
//        Calvin_file calvinFile5 = calvinFileService.getFileOriginName(boardView.getFile_code5());
//        model.addAttribute("boardView", boardView);
//        model.addAttribute("file1",calvinFile1);
//        model.addAttribute("file2",calvinFile2);
//        model.addAttribute("file3",calvinFile3);
//        model.addAttribute("file4",calvinFile4);
//        model.addAttribute("file5",calvinFile5);
//        model.addAttribute("page_type", "8.5");
//        String board_type = boardView.getBoard_type();
//        if(board_type.equals("공지사항")){
//            model.addAttribute("page_type", "8.5");
//        }else if(board_type.equals("사진자료실")){
//            model.addAttribute("page_type", "8.6");
//        }else if(board_type.equals("서식자료실")){
//            model.addAttribute("page_type", "8.7");
//        }
//        return "menu/board/board_view";
//    }
//    //게시글 작성 페이지
//    @GetMapping("/menu/board/write")
//    public String BoardWrite(HttpSession httpSession){
//        if(httpSession.getAttribute("member_id") != null && httpSession.getAttribute("member_type") != null){
//            if(httpSession.getAttribute("member_type").equals("dd")||httpSession.getAttribute("member_type").equals("st")||httpSession.getAttribute("member_type").equals("ai")) {
//
//            }
//        }else {
//            throw new CustomException(ErrorCode.INVALID_PERMISSION);
//        }
//        return "menu/board/board_write";
//    }

//    //게시글 작성
//    @PostMapping("/menu/board/write")
//    public String InsertBoard(@RequestParam(value = "title") String title, @RequestParam(value = "contents") String board_contents,
//                              @RequestParam(value = "member_id") String member_id,
//                              @RequestParam(value= "board_type") String board_type,
//                              @RequestParam(value = "file1", required = false) MultipartFile file1,
//                              @RequestParam(value = "file2", required = false) MultipartFile file2,
//                              @RequestParam(value = "file3", required = false) MultipartFile file3,
//                              @RequestParam(value = "file4", required = false) MultipartFile file4,
//                              @RequestParam(value = "file5", required = false) MultipartFile file5){
//        String result = "";
//        List<MultipartFile> file_list = new ArrayList<>();
//        boolean token = false;
//        if(file1 != null){
//            file_list.add(file1);
//            token = true;
//        }
//        if(file2 != null){
//            file_list.add(file2);
//            token = true;
//        }
//        if(file3 != null){
//            file_list.add(file3);
//            token = true;
//        }
//        if(file4 != null){
//            file_list.add(file4);
//            token = true;
//        }
//        if(file5 != null){
//            file_list.add(file5);
//            token = true;
//        }
//        int insert_result;
//        if(token){
//            insert_result = calvinBoardService.insertBoard(title,board_contents,member_id,file_list,board_type);
//        }else{
//            insert_result = calvinBoardService.insertBoard(title,board_contents,member_id,board_type);
//        }
//        if(insert_result == 1){
//            result = "redirect:/menu/board";
//        }else{
//            //insert 실패시
//            result = "redirect:/menu/board";
//        }
//        return result;
//    }

//    //게시글 삭제
//    @GetMapping("/menu/board/delete")
//    @ResponseBody
//    public String boardDelete(@RequestParam(value = "board_code") int board_code, HttpSession httpSession, Model model){
//        String result;
//        if(httpSession.getAttribute("member_id") == null || httpSession.getAttribute("member_type") == null){
//            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
//        }else{
//            if(httpSession.getAttribute("member_type").equals("dd") || httpSession.getAttribute("member_type").equals("ai") || httpSession.getAttribute("member_type").equals("st")){
//                int dt_result = calvinBoardService.DeleteBoard(board_code);
//                if(dt_result == 1){
//                    result = "<script>alert('게시글이 삭제되었습니다.');history.back();</script>";
//                }else{
//                    result = "<script>alert('게시글 삭제에 실패했습니다.');history.back();</script>";
//                }
//            }else {
//                throw new CustomException(ErrorCode.INVALID_PERMISSION);
//            }
//        }
//
//        return result;
//    }
}
