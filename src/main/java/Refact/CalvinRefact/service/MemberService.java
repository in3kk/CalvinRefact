package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.repository.MemberDataJpaRepository;
import Refact.CalvinRefact.repository.Member_SubjectRepository;
import Refact.CalvinRefact.repository.dto.member.JoinMemberDto;
import Refact.CalvinRefact.repository.dto.member.MemberDetailDto;
import Refact.CalvinRefact.repository.dto.member.MemberListDto;
import Refact.CalvinRefact.repository.dto.member.MemberSubjectListDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    Member_SubjectRepository memberSubjectRepository;

    //권한 유효성 검사
    public boolean permissionCheck(Long id){
        boolean result = false;
        Optional<Member> memberOptional = memberDataJpaRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getMember_type().equals(Member_Type.admin)||member.getMember_type().equals(Member_Type.developer)||member.getMember_type().equals(Member_Type.professor)) {
                result = true;
            }
        }
        return result;
    }
    public boolean permissionCheck(String email){
        boolean result = false;
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getMember_type().equals(Member_Type.admin)||member.getMember_type().equals(Member_Type.developer)||member.getMember_type().equals(Member_Type.professor)) {
                result = true;
            }
        }
        return result;
    }

    //회원가입 유효성 검사 메소드
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


    //로그인 메소드
    @Transactional
    public Optional<Member> login(String id, String pwd){
        return memberDataJpaRepository.findByEmailAndPwd(id,pwd);
    }

    //회원 권한 변경
    @Transactional
    public boolean memberGrant(Long id, String member_type,Member_Type target_member_type){
        boolean result = false;
        if (member_type.equals("dd") || member_type.equals("st") || member_type.equals("ai")) {
            Optional<Member> findResult = memberDataJpaRepository.findById(id);
            if(findResult.isPresent()){
                Member member = findResult.get();
                member.setMember_type(target_member_type);
                result = true;
            }
        }
        return result;
    }

    //회원 삭제 메소드
    @Transactional
    public boolean deleteMember(Long id,String member_type) {
        boolean result = false;
        if (member_type.equals("dd") || member_type.equals("st") || member_type.equals("ai")) {
            memberDataJpaRepository.deleteById(id);
            result = true;
        }
        return result;
    }


    //회원 리스트
    public Page<MemberListDto> findAll(String email, Pageable pageable){
        Page<MemberListDto> memberListDtos = Page.empty();
        Page<Member> members;
        if(permissionCheck(email)){
            members = memberDataJpaRepository.findAll(pageable);
            memberListDtos = members.map(member -> new MemberListDto(member.getId(),member.getEmail(),member.getName(),member.getPhone_number(),member.getMember_type()));
        }
        return memberListDtos;
    }

    //회원 리스트 by email
    public Page<MemberListDto> findAllByEmail(String email, String search_word, Pageable pageable) {
        Page<MemberListDto> memberListDtos = Page.empty();
        Page<Member> members;
        if(permissionCheck(email)){
            members = memberDataJpaRepository.findByEmailContaining(search_word, pageable);
            memberListDtos = members.map(member -> new MemberListDto(member.getId(),member.getEmail(),member.getName(),member.getPhone_number(),member.getMember_type()));
        }
        return memberListDtos;
    }

    //회원 리스트 by username
    public Page<MemberListDto> findAllByUsername(String email, String search_word, Pageable pageable) {
        Page<MemberListDto> memberListDtos = Page.empty();
        Page<Member> members;
        if(permissionCheck(email)){
            members = memberDataJpaRepository.findByNameContaining(search_word,pageable);
            memberListDtos = members.map(member -> new MemberListDto(member.getId(),member.getEmail(),member.getName(),member.getPhone_number(),member.getMember_type()));
        }
        return memberListDtos;
    }

    //회원 정보 보기
    public MemberDetailDto findMemberDetail(String email, Long id){
        MemberDetailDto memberDetailDto = new MemberDetailDto();
        if(permissionCheck(email)){
            Optional<Member> memberOptional = memberDataJpaRepository.findById(id);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                memberDetailDto = new MemberDetailDto(member.getId(),member.getName(),member.getEmail(),
                        member.getPhone_number(),member.getAddress(),member.getBirth(),
                        member.getCreatedDate(),member.getMember_type());
            }
        }
        return memberDetailDto;
    }
    //내 정보 보기
    public MemberDetailDto findMemberDetail2(String email){
        MemberDetailDto memberDetailDto = new MemberDetailDto();

        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberDetailDto = new MemberDetailDto(member.getId(),member.getName(),member.getEmail(),
                    member.getPhone_number(),member.getAddress(),member.getBirth(),
                    member.getCreatedDate(),member.getMember_type());
        }

        return memberDetailDto;
    }

    //내 강의 리스트
    public List<MemberSubjectListDto> findMemberSubjectList(String email,Pageable pageable) {
        return memberSubjectRepository.findMySubjectByEmail(email,pageable);
    }

//    //pwd 변경
//    public void updatePwd(String newPwd, String email){
//        memberDataJpaRepository.
//    }
}
