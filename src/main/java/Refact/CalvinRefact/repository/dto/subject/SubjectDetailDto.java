package Refact.CalvinRefact.repository.dto.subject;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Type;
import Refact.CalvinRefact.repository.dto.file.FileSimpleDto;
import lombok.Data;

/**
 * Calvin_Subject -> SubjectListDto, SubjectDetailDto 세분화
 */
@Data
public class SubjectDetailDto {

    private Long subject_code;
    private FileSimpleDto fileSimpleDto;
    private String subject_name;
    private String member_name;
    private Subject_Field subject_field;
    private Subject_Type subject_type;
    private Subject_Stat subject_stat;
    private String lecture_time;
    private int fee;
    private String period;
    private int personnel;

    public SubjectDetailDto(Long subject_code, FileSimpleDto fileSimpleDto, String subject_name, String member_name, Subject_Field subject_field, Subject_Type subject_type, Subject_Stat subject_stat, String lecture_time, int fee, String period, int personnel) {
        this.subject_code = subject_code;
        this.fileSimpleDto = fileSimpleDto;
        this.subject_name = subject_name;
        this.member_name = member_name;
        this.subject_field = subject_field;
        this.subject_type = subject_type;
        this.subject_stat = subject_stat;
        this.lecture_time = lecture_time;
        this.fee = fee;
        this.period = period;
        this.personnel = personnel;
    }

    public SubjectDetailDto(Long subject_code, String subject_name, String member_name, Subject_Field subject_field, Subject_Type subject_type, Subject_Stat subject_stat, String lecture_time, int fee, String period, int personnel) {
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.member_name = member_name;
        this.subject_field = subject_field;
        this.subject_type = subject_type;
        this.subject_stat = subject_stat;
        this.lecture_time = lecture_time;
        this.fee = fee;
        this.period = period;
        this.personnel = personnel;
    }

    public SubjectDetailDto() {
    }
}
