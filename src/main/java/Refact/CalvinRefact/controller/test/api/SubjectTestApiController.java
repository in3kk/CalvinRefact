package Refact.CalvinRefact.controller.test.api;

import Refact.CalvinRefact.service.SubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectTestApiController {

    @Autowired
    SubjectService subjectService;

    //테스트 시작전 "member_id" 세션 생성 필요
    @GetMapping("/menu/subject/apply/pro")
    @ResponseBody
    public ResponseEntity<?> ApplyPro(HttpSession httpSession, @RequestParam(value = "subject_code") Long subject_code){
        ResponseEntity<?> result;
        if(httpSession.getAttribute("member_id")==null){
            result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인이 필요한 서비스입니다.");
        }else{
            String member_id = httpSession.getAttribute("member_id").toString();
            if(subjectService.applyWhether(member_id,subject_code)){
                boolean insert_result = subjectService.applyPro(member_id,subject_code);
                if(insert_result){
                    result = ResponseEntity.ok("수강신청이 완료되었습니다.");
                }else{
                    result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수강신청이 실패했습니다.");
                }
            }else{
                result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 수강신청한 강의입니다.");
            }
        }
        return result;
    }
}
