package Refact.CalvinRefact.repository.dto.member;

import Refact.CalvinRefact.entity.entityEnum.Pay_Stat;
import Refact.CalvinRefact.entity.entityEnum.Subject_Field;
import lombok.Data;

@Data
public class MemberSubjectListDto {


    private Long subject_id;
    private String subject_name;
    private Subject_Field subject_field;
    private int fee;
    private Long member_subject_id;
    private Long member_id;
    private Pay_Stat pay_stat;


}
