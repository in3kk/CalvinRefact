package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.repository.MemberDataJpaRepository;
import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MemberService {

    @Autowired
    CalvinService calvinService;

    @Autowired
    EntityManager em;

    @Autowired
    MemberDataJpaRepository memberDataJpaRepository;

    @Transactional
    public boolean joinMember(JoinMemberDto joinMemberDto){

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
            Address address = new Address(address1,address2);
            Member member = new Member(id1+"@"+id2, pwd, name, Member_Type.member, birth, pnum, address);
            em.persist(member);
            if(em.contains(member)){
                result = true;
            }
        }

        return result;
    }


    @Transactional
    public Optional<Member> login(String id, String pwd){
        return memberDataJpaRepository.findByEmailAndPwd(id,pwd);
    }


}
