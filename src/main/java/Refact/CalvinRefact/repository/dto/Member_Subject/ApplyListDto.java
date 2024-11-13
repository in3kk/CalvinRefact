package Refact.CalvinRefact.repository.dto.Member_Subject;

import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import lombok.Data;

/**
 * MyPageSubjectView -> ApplyListDto 세분화
 */
@Data
public class ApplyListDto {
    private Long apply_code;
    private String subject_name;
    private Subject_Field subject_field;
    private String name;
    private String email;
    private Pay_Stat pay_stat;

    public ApplyListDto(Long apply_code, String subject_name, Subject_Field subject_field, String name, String email, Pay_Stat pay_stat) {
        this.apply_code = apply_code;
        this.subject_name = subject_name;
        this.subject_field = subject_field;
        this.name = name;
        this.email = email;
        this.pay_stat = pay_stat;
    }

    public ApplyListDto() {
    }
}
