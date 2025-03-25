package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.File;
import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.Subject;
import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.exception.InvalidPermissionException;
import Refact.CalvinRefact.repository.*;
import Refact.CalvinRefact.repository.dto.Member_Subject.ApplyListDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectDetailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    FileService fileService;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    EntityManager em;

    public Page<SubjectListDto> findSubjectList(Pageable pageable, String email) throws InvalidPermissionException{
        Page<SubjectListDto> result = Page.empty();
        memberService.permissionCheck(email);

        result = subjectRepository.findAllSubjectList(pageable);

        return result;
    }

    public Page<SubjectListDto> findSubjectListByName(Pageable pageable, String search_word, String email) throws InvalidPermissionException{
        Page<SubjectListDto> result = Page.empty();
        memberService.permissionCheck(email);

        result = subjectRepository.findSubjectListByName(pageable, search_word);

        return result;
    }

    public Page<SubjectListDto> findSubjectListByField(Pageable pageable, String search_word, String email) throws InvalidPermissionException{
        Page<SubjectListDto> result = Page.empty();
        memberService.permissionCheck(email);

        result = subjectRepository.findSubjectListByField(pageable, search_word);

        return result;
    }

    public Page<SubjectListDto> findSubjectListByType(Pageable pageable, String search_word, String email) throws InvalidPermissionException{
        Page<SubjectListDto> result = Page.empty();
        memberService.permissionCheck(email);

        result = subjectRepository.findSubjectListByType(pageable, search_word);

        return result;
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType) {
        return subjectRepository.findByType(subjectType);
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType, Subject_Field subject_field) {
        return subjectRepository.findByTypeAndField(subjectType, subject_field);
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType, Subject_Field subjectField, String name) {
        return subjectRepository.findByTypeAndFieldAndName(subjectType, subjectField, name);
    }

    //강의 디테일
    public SubjectDetailDto findSubjectDetail(Long id) {
        SubjectDetailDto subjectDetailDto = subjectRepository.findSubjectDetailById(id);
        subjectDetailDto.setFileSimpleDto(fileRepository.findFileSimpleById(subjectDetailDto.getSubject_code()));
//        System.out.println("파일 : "+subjectDetailDto.getFileSimpleDto().getOriginal_name());
        return subjectDetailDto;
    }

    //신청 확인
    public boolean applyWhether(String email, Long id) {
        return memberSubjectRepository.findApplyExistById(email, id);
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
    public Page<ApplyListDto> findApplyList(Pageable pageable, String email) throws InvalidPermissionException{
        Page<ApplyListDto> result = Page.empty();
        memberService.permissionCheck(email);

        result = memberSubjectRepository.findApplyList(pageable);

        return result;
    }

    //수강 신청 리스트 by subject_name or email(어드민)
    public Page<ApplyListDto> findApplyListBy(Pageable pageable, int search_type, String search_word, String email) throws InvalidPermissionException {
        Page<ApplyListDto> result = Page.empty();
        memberService.permissionCheck(email);

        if (search_type == 1) {//subject_name
            result = memberSubjectRepository.findApplyListBySubject_name(pageable, search_word);
        } else if (search_type == 2) {//email
            result = memberSubjectRepository.findApplyListByEmail(pageable, search_word);
        }

        return result;
    }

    //수강 신청 취소(어드민) Exception
    @Transactional(rollbackFor = {Exception.class})
    public void deleteApply(List<Long> applyIdList, String email) throws Exception{

        memberService.permissionCheck(email);

        for (Long applyId : applyIdList) {
            member_subjectDataJpaRepository.deleteById(applyId);
        }
        em.flush();
        em.clear();
    }

    //Pay_stat 변경 Exception
    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public void updatePayStat(List<Long> applyIdList, int type, String email) throws Exception{

        memberService.permissionCheck(email);
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
            memberSubjectRepository.updatePay_stat(applyId, pay_stat);
        }
    }
    //강의 상태 변경 Exception
    @Transactional(rollbackFor = {Exception.class},isolation = Isolation.SERIALIZABLE)
    public void updateSubjectStat(Long id, int stat,String email) throws Exception{

        memberService.permissionCheck(email);

        Optional<Subject> subjectOptional = subjectDataJpaRepository.findById(id);
        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();
            Subject_Stat subject_stat = Subject_Stat.임시;
            switch (stat) {
                case 1:
                    subject_stat = Subject_Stat.접수중;
                    break;
                case 2:
                    subject_stat = Subject_Stat.마감;
                    break;
                case 3:
                    subject_stat = Subject_Stat.폐강;
                    break;
            }
            subject.setSubject_stat(subject_stat);
            subjectDataJpaRepository.save(subject);

        }
    }
    //신규 강의 생성 Exception
    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public boolean saveSubject(String email,String subject_name, Subject_Field subject_field, Subject_Type subject_type, int personnel, String lecture_time, String period, Long member_code, int fee, Long subject_code,MultipartFile file) throws Exception{
        memberService.permissionCheck(email);
        boolean result = false;
        Subject subject = new Subject();
        Optional<Member> memberOptional = memberDataJpaRepository.findById(member_code);
        Member member = new Member();
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
        }
        if (subject_code == -1) {
            subject = new Subject(member,fee,subject_name,subject_field,Subject_Stat.임시,lecture_time,period,personnel,subject_type);
        }else{
            Optional<Subject> subjectOptional = subjectDataJpaRepository.findById(subject_code);
            if (subjectOptional.isPresent()) {
                subject = subjectOptional.get();
                subject.setFee(fee);
                subject.setSubject_field(subject_field);
                subject.setSubject_name(subject_name);
                subject.setLecture_time(lecture_time);
                subject.setPeriod(period);
                subject.setPersonnel(personnel);
                subject.setSubject_type(subject_type);
            }
        }
        if (!file.isEmpty()) {
            File fileResult  = fileService.saveFile(file);
            subject.setFile(fileResult);
        }
        subjectDataJpaRepository.save(subject);

        if (subject.getSubject_name() != null) {
            result = true;
        }
        return result;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteSubject(Long id,String email) throws Exception{
        memberService.permissionCheck(email);

        Optional<Subject> subjectOptional = subjectDataJpaRepository.findById(id);
        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();

            member_subjectDataJpaRepository.deleteAllBySubject(subject);
            subjectDataJpaRepository.deleteById(id);
        }
    }

    public boolean applyCheck(String email, Long subject_id) {
        boolean token = true;
        Optional<Member> memberOptional = memberDataJpaRepository.findByEmail(email);
        Member member = new Member();
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
        }else {
            token = false;
        }
        Optional<Subject> subjectOptional = subjectDataJpaRepository.findById(subject_id);
        Subject subject = new Subject();
        if (subjectOptional.isPresent()) {
            subject = subjectOptional.get();
        } else {
            token = false;
        }
        Long findResult;
        if (token) {
            findResult = member_subjectDataJpaRepository.countFirstByMemberAndSubject(member,subject);
            if (findResult <= 0) {
                token = false;
            }
        }
        return token;
    }
}
