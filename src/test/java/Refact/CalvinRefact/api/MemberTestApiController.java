package Refact.CalvinRefact.api;

import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class MemberTestApiController {
//    @PostMapping("/test/join")
//    @ResponseBody
//    public ResponseEntity<Boolean> JoinTest(@RequestParam String id,
//                                            @RequestParam String id2,
//                                            @RequestParam String pwd,
//                                            @RequestParam String name,
//                                            @RequestParam LocalDate birth,
//                                            @RequestParam String phone_number,
//                                            @RequestParam String address,
//                                            @RequestParam String address2) {
//
//        JoinMemberDto jm = new JoinMemberDto();
//        jm.setId(id);
//        jm.setId2(id2);
//        jm.setPwd(pwd);
//        jm.setName(name);
//        jm.setBirth(birth);
//        jm.setPhone_number(phone_number);
//        jm.setAddress(address);
//        jm.setAddress2(address2);
//    }
}
