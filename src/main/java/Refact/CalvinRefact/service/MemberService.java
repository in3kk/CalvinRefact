package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import Refact.CalvinRefact.exception.InvalidMemberDataException;
import Refact.CalvinRefact.exception.InvalidPermissionException;
import Refact.CalvinRefact.repository.MemberDataJpaRepository;
import Refact.CalvinRefact.repository.MemberRepository;
import Refact.CalvinRefact.repository.Member_SubjectRepository;
import Refact.CalvinRefact.repository.dto.member.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    @Autowired
    MemberRepository memberRepository;

    //권한 유효성 검사
    public void permissionCheck(Long id){
        boolean result = false;
        Optional<Member> memberOptional = memberDataJpaRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getMemberType().equals(Member_Type.admin)||member.getMemberType().equals(Member_Type.developer)||member.getMemberType().equals(Member_Type.professor)) {
                result = true;
            }
        }
        if (!result) {
            throw new InvalidPermissionException("권한이 부족합니다.");
        }
    }
    public void permissionCheck(String email) throws InvalidPermissionException{
        boolean result = false;
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getMemberType().equals(Member_Type.admin)||member.getMemberType().equals(Member_Type.developer)||member.getMemberType().equals(Member_Type.professor)) {
                result = true;
            }
        }
        if (!result) {
            throw new InvalidPermissionException("권한이 부족합니다.");
        }
    }


    //회원가입 유효성 검사 메소드
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean saveMember(JoinMemberDto joinMemberDto) throws InvalidMemberDataException{

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


    //로그인 메소드
    @Transactional
    public Optional<Member> login(String id, String pwd){
        return memberDataJpaRepository.findByEmailAndPwd(id,pwd);
    }

    //회원 권한 변경 Exception
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void memberGrant(String email, Long id, String member_type,Member_Type target_member_type) throws Exception{
        permissionCheck(email);
        Optional<Member> findResult = memberDataJpaRepository.findById(id);
        if(findResult.isPresent()){
            Member member = findResult.get();
            member.setMemberType(target_member_type);
        }
    }

    //회원 삭제 메소드 Exception
    @Transactional(rollbackFor = {Exception.class})
    public void deleteMember(String email,Long id,String member_type) throws Exception{
        permissionCheck(email);
        memberDataJpaRepository.deleteById(id);
    }


    //회원 리스트
    public Page<MemberListDto> findAll(String email, Pageable pageable) throws InvalidPermissionException{
        permissionCheck(email);
        Page<MemberListDto> memberListDtos = memberRepository.findAll(pageable);

        return memberListDtos;
    }

    //회원 리스트 by email
    public Page<MemberListDto> findAllByEmail(String email, String search_word, Pageable pageable) throws InvalidPermissionException{
        permissionCheck(email);
        Page<MemberListDto> memberListDtos = memberRepository.findAllByEmail(search_word, pageable);

        return memberListDtos;
    }

    //회원 리스트 by username
    public Page<MemberListDto> findAllByUsername(String email, String search_word, Pageable pageable) throws InvalidPermissionException{
        permissionCheck(email);
        Page<MemberListDto> memberListDtos = memberRepository.findAllByUsername(search_word,pageable);

        return memberListDtos;
    }

    //회원 정보 보기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MemberDetailDto findMemberDetail(String email, Long id) throws InvalidPermissionException{
        MemberDetailDto memberDetailDto = new MemberDetailDto();
        permissionCheck(email);

        Optional<Member> memberOptional = memberDataJpaRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberDetailDto = new MemberDetailDto(member.getId(),member.getName(),member.getEmail(),
                    member.getPhone_number(),member.getAddress(),member.getBirth(),
                    member.getCreatedDate(),member.getMemberType());
        }

        return memberDetailDto;
    }
    //내 정보 보기
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public MemberDetailDto findMemberDetail2(String email){
        MemberDetailDto memberDetailDto = new MemberDetailDto();

        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberDetailDto = new MemberDetailDto(member.getId(),member.getName(),member.getEmail(),
                    member.getPhone_number(),member.getAddress(),member.getBirth(),
                    member.getCreatedDate(),member.getMemberType());
        }

        return memberDetailDto;
    }

    //내 강의 리스트
    public List<MemberSubjectListDto> findMemberSubjectList(String email,Pageable pageable) {
        return memberSubjectRepository.findMySubjectByEmail(email,pageable);
    }

    //pwd 변경
    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public boolean updateMember( String email,String newPwd, String newPnum, String address, String address2){
        boolean result = false;
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            if(!newPwd.equals("")){
                member.setPwd(newPwd);
                result = true;
            }
            if(!newPnum.equals("")){
                member.setPhone_number(newPnum);
                result = true;
            }
            if(!address.equals("")&&!address2.equals("")){
                member.setAddress(new Address(address,address2));
                result = true;
            }
        }
        return result;
    }

    public List<MemberEmailDto> findProfessorList(String email) throws InvalidPermissionException{
        List<MemberEmailDto> memberEmailDtos = new ArrayList<>();
        permissionCheck(email);

        memberEmailDtos = memberDataJpaRepository.findProfessorByMemberType(Member_Type.professor);

        return memberEmailDtos;
    }
}
