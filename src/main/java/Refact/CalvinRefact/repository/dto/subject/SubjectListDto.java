package Refact.CalvinRefact.repository.dto.subject;

import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import Refact.CalvinRefact.entity.entityEnum.Subject_Stat;
import lombok.Data;

/**
 * Calvin_Subject -> SubjectListDto, SubjectDetailDto 로 세분화
 */
@Data
public class SubjectListDto {
    private Long id;
    private int fee;
    private String subject_name;
    private Subject_Field subject_field;
    private Subject_Stat subject_stat;
    private String lecture_time;
    private String period;
    private int personnel;

    public SubjectListDto(Long id, int fee, String subject_name, Subject_Field subject_field, Subject_Stat subject_stat, String lecture_time, String period, int personnel) {
        this.id = id;
        this.fee = fee;
        this.subject_name = subject_name;
        this.subject_field = subject_field;
        this.subject_stat = subject_stat;
        this.lecture_time = lecture_time;
        this.period = period;
        this.personnel = personnel;
    }

    public SubjectListDto() {
    }
}
