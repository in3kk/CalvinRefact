package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.Subject;
import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.*;
import Refact.CalvinRefact.repository.dto.Member_Subject.ApplyListDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectDetailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    FileDataJpaRepository fileDataJpaRepository;
    @Autowired
    Member_SubjectRepository memberSubjectRepository;
    @Autowired
    MemberDataJpaRepository memberDataJpaRepository;
    @Autowired
    SubjectDataJpaRepository subjectDataJpaRepository;
    @Autowired
    Member_SubjectDataJpaRepository member_subjectDataJpaRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType) {
        return subjectRepository.findByType(subjectType);
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType, Subject_Field subject_field) {
        return subjectRepository.findByTypeAndField(subjectType, subject_field);
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType, Subject_Field subjectField, String name) {
        return subjectRepository.findByTypeAndFieldAndName(subjectType,subjectField,name);
    }

    //강의 디테일
    public SubjectDetailDto findSubjectDetail(Long id) {
        SubjectDetailDto subjectDetailDto = subjectRepository.findSubjectDetailById(id);
        subjectDetailDto.setFileSimpleDto(fileDataJpaRepository.findSimpleFileByBoardId(subjectDetailDto.getSubject_code()));
        return  subjectDetailDto;
    }

    //신청 확인
    public boolean applyWhether(String email, Long id) {
        return memberSubjectRepository.findApplyExistById(email,id);
    }

    //수강 신청
    @Transactional(rollbackFor = {Exception.class})
    public boolean applyPro(String email, Long id) {
        boolean result = false;
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        Optional<Subject> subjectOptional = subjectDataJpaRepository.findById(id);
        if (memberOptional.isPresent() && subjectOptional.isPresent()) {
            Member_Subject member_subject = new Member_Subject(memberOptional.get(), subjectOptional.get(), LocalDate.now(), Pay_Stat.n);
            member_subjectDataJpaRepository.save(member_subject);
            result = true;
        }
        return result;
    }

    //수강 신청 리스트(어드민)
    public Page<ApplyListDto> findApplyList(Pageable pageable, String email) {
        Page<ApplyListDto> result = Page.empty();
        if (memberService.permissionCheck(email)) {
            result = memberSubjectRepository.findApplyList(pageable);
        }
        return result;
    }

    //수강 신청 리스트 by subject_name or email(어드민)
    public Page<ApplyListDto> findApplyListBy(Pageable pageable, int search_type,String search_word, String email) {
        Page<ApplyListDto> result = Page.empty();
        if (memberService.permissionCheck(email)) {
            if(search_type == 1){//subject_name
                result = memberSubjectRepository.findApplyListBySubject_name(pageable, search_word);
            } else if (search_type == 2) {//email
                result = memberSubjectRepository.findApplyListByEmail(pageable, search_word);
            }
        }
        return result;
    }

    //수강 신청 취소(어드민)
    @Transactional(rollbackFor = {Exception.class})
    public boolean deleteApply(List<Long> applyIdList,String email) {
        boolean result = false;
        if (memberService.permissionCheck(email)) {
            for (Long applyId : applyIdList) {
                member_subjectDataJpaRepository.deleteById(applyId);
            }
            em.flush();
            em.clear();
            result = true;
        }else {
            result = false;
            /**
             * 권한 부족 예외 추가
             */
        }
        return result;
    }

    //Pay_stat 변경
    @Transactional(rollbackFor = {Exception.class})
    public boolean updatePayStat(List<Long> applyIdList, int type, String email) {
        boolean result = false;
        if (memberService.permissionCheck(email)) {
            Pay_Stat pay_stat = Pay_Stat.n;
            switch (type) {
                case 1:
                    pay_stat = Pay_Stat.y;//yes
                    break;
                case 2:
                    pay_stat = Pay_Stat.n;//no
                    break;
                case 3:
                    pay_stat = Pay_Stat.r;//refund
                    break;
            }
            for (Long applyId : applyIdList) {
                result = memberSubjectRepository.updatePay_stat(applyId, pay_stat);
            }
        }else {
            /**
             * 권한 부족 예외 추가
             */
        }
        return result;
    }
}
