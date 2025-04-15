package Refact.CalvinRefact.controller.test.api;

import Refact.CalvinRefact.entity.entityEnum.Board_Type;
import Refact.CalvinRefact.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
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
                                      @RequestParam(value= "board_type") String board_type,
                                      @RequestParam(value = "file1", required = false) MultipartFile file1,
                                      @RequestParam(value = "file2", required = false) MultipartFile file2,
                                      @RequestParam(value = "file3", required = false) MultipartFile file3,
                                      @RequestParam(value = "file4", required = false) MultipartFile file4,
                                      @RequestParam(value = "file5", required = false) MultipartFile file5){
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
            result= ResponseEntity.ok(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = ResponseEntity.ok(false);
        }
        return result;
    }
}
