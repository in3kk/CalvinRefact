package Refact.CalvinRefact.service;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.SubjectRepository;
import Refact.CalvinRefact.repository.dto.subject.SubjectListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    public List<SubjectListDto> findSubjectList(Subject_Type subjectType) {
        return subjectRepository.findByType(subjectType);
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType, Subject_Field subject_field) {
        return subjectRepository.findByTypeAndField(subjectType, subject_field);
    }

    public List<SubjectListDto> findSubjectList(Subject_Type subjectType, Subject_Field subjectField, String name) {
        return subjectRepository.findByTypeAndFieldAndName(subjectType,subjectField,name);
    }
}
