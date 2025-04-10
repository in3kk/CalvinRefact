package Refact.CalvinRefact.repository;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.exception.InvalidMemberDataException;
import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import Refact.CalvinRefact.service.CalvinService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class MemberServiceTest {
    @Autowired
    CalvinService calvinService;
    @Autowired
    MemberDataJpaRepository memberDataJpaRepository;
    @Autowired
    EntityManager em;
    //회원가입 유효성 검사 메소드
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean saveMember(JoinMemberDto joinMemberDto) throws InvalidMemberDataException {

        String id1 = joinMemberDto.getId();
        String id2 = joinMemberDto.getId2();
        String pwd = joinMemberDto.getPwd();
        String name = joinMemberDto.getName();
        LocalDate birth =joinMemberDto.getBirth();
        String pnum = joinMemberDto.getPhone_number();
        String address1 = joinMemberDto.getAddress();
        String address2 = joinMemberDto.getAddress2();


        Pattern id1_pattern = Pattern.compile("[A-Za-z0-9]{4,15}");
        Pattern id2_pattern = Pattern.compile("[a-z]{4,10}.(com|net|ac.kr)");
        Pattern pwd_pattern = Pattern.compile("[a-zA-Z0-9!@#$%^&\\*()_\\+]{10,25}");
        Pattern name_pattern = Pattern.compile("[가-힣A-Za-z]{2,10}");
//        Pattern birth_pattern = Pattern.compile("(19[0-9][0-9]|20[0-9]{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])");
        Pattern pnum_pattern = Pattern.compile("0[0-9]{1,2}[0-9]{3,4}[0-9]{4}");
        Pattern address1_pattern = Pattern.compile("[가-힣0-9\\s-]*");
        Pattern address2_pattern = Pattern.compile("[가-힣0-9\\s-]*");

        boolean result1 = calvinService.validationMethod(id1, id1_pattern);
        boolean result2 = calvinService.validationMethod(id2,id2_pattern);
        boolean result3 = calvinService.validationMethod(pwd,pwd_pattern);
        boolean result4 = calvinService.validationMethod(name,name_pattern);
        boolean result6 = calvinService.validationMethod(pnum,pnum_pattern);
        boolean result7 = calvinService.validationMethod(address1,address1_pattern);
        boolean result8 = calvinService.validationMethod(address2,address2_pattern);

        boolean result = false;
        if (result1 && result2 && result3 && result4 && result6 && result7 && result8) {
            Address address = new Address(address1, address2);
            Member member = new Member(id1 + "@" + id2, pwd, name, Member_Type.member, birth, pnum, address);
            memberDataJpaRepository.save(member);
            if (em.contains(member)) {
                result = true;
            }
        } else {
            String message = "";
            if (!result1||!result2) {
                message = "올바르지 않은 이메일 형식입니다.";
            } else if (!result3) {
                message = "올바르지 않은 비밀번호 형식입니다.";
            } else if (!result4) {
                message = "올바르지 않은 이름 형식입니다.";
            } else if (!result6) {
                message = "올바르지 않은 전화번호 형식입니다.";
            } else if (!result7||!result8) {
                message = "올바르지 않은 주소 형식입니다.";
            }
            throw new InvalidMemberDataException(message);
        }

        return result;
    }
}
