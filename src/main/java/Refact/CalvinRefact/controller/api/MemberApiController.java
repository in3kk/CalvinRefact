package Refact.CalvinRefact.controller.api;

import Refact.CalvinRefact.repository.MemberDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MemberApiController {

    @Autowired
    private MemberDataJpaRepository memberDataJpaRepository;
    @PostMapping("/id_dup_check")
    @ResponseBody
    public ResponseEntity<Boolean> IdDuplicateCheck(@RequestBody Map<String, Object> user_id){
        String id = user_id.get("user_id").toString();
        Long rowCount = memberDataJpaRepository.countByEmail(id);
        boolean result;
        if(rowCount == 1L){
            result = true;
        }else{
            result = false;
        }
        return ResponseEntity.ok(result);
    }
}
