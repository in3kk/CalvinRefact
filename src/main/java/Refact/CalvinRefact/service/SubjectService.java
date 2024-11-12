package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.Member;
import Refact.CalvinRefact.entity.Member_Subject;
import Refact.CalvinRefact.entity.Subject;
import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.*;
import Refact.CalvinRefact.repository.dto.subject.SubjectDetailDto;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
}
