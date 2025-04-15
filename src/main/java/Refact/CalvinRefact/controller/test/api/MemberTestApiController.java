package Refact.CalvinRefact.controller.test.api;

import Refact.CalvinRefact.exception.InvalidMemberDataException;
import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import Refact.CalvinRefact.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class MemberTestApiController {
    @Autowired
    MemberService memberService;

    @PostMapping("/test/member/join")
    @ResponseBody
    public ResponseEntity<Boolean> JoinTest(@RequestParam String id,
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
        } catch (InvalidMemberDataException e) {
            System.out.println(e.getMessage());
        }
         return ResponseEntity.ok(joinResult);
    }
}
