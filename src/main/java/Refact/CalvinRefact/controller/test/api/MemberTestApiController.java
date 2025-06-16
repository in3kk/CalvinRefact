package Refact.CalvinRefact.controller.test.api;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.exception.InvalidMemberDataException;
import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import Refact.CalvinRefact.repository.dto.member.MemberLoginDto;
import Refact.CalvinRefact.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
public class MemberTestApiController {
    @Autowired
    MemberService memberService;

    @PostMapping("/test/member/join")
    @ResponseBody
    public ResponseEntity<?> JoinTest(@RequestParam String id,
                                            @RequestParam String id2,
                                            @RequestParam String pwd,
                                            @RequestParam String name,
                                            @RequestParam String phone_number,
                                            @RequestParam String address,
                                            @RequestParam String address2) {

        JoinMemberDto jm = new JoinMemberDto();
        jm.setId(id);
        jm.setId2(id2);
        jm.setPwd(pwd);
        jm.setName(name);
        jm.setBirth(LocalDate.now());
        jm.setPhone_number(phone_number);
        jm.setAddress(address);
        jm.setAddress2(address2);
        boolean joinResult = false;
        try {
            joinResult = memberService.saveMember(jm);
            return ResponseEntity.ok(joinResult);
        } catch (InvalidMemberDataException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("올바르지 않은 입력입니다.");
        } catch (Exception e) {
            if (e instanceof SQLException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이메일 입니다.");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("알 수 없는 예외가 발생했습니다. : "+e.getMessage());
            }
        }
    }

    @PostMapping("/test/member/login")
    @ResponseBody
    public ResponseEntity<?> loginTest(HttpSession httpSession,
                                             @Valid MemberLoginDto memberLoginDto) {
        Optional<Member> member = memberService.login(memberLoginDto.getId(),memberLoginDto.getPwd());
        ResponseEntity<?> result;
        if(member.isPresent()){
            httpSession.setAttribute("member_id", member.get().getEmail());
            Member_Type member_type = member.get().getMemberType();
            if(member_type.equals(Member_Type.member)){
                httpSession.setAttribute("member_type", "mb");
            }else if(member_type.equals(Member_Type.developer)){
                httpSession.setAttribute("member_type", "dd");
            }else if(member_type.equals(Member_Type.professor)){
                httpSession.setAttribute("member_type","st");
            }else if(member_type.equals(Member_Type.admin)){
                httpSession.setAttribute("member_type","ai");
            }
            result =  ResponseEntity.ok(true);
        }else{
            result =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일치하는 회원 정보가 없습니다.");
        }
        httpSession.removeAttribute("member_id");
        httpSession.removeAttribute("member_type");
        return result;
    }
}
