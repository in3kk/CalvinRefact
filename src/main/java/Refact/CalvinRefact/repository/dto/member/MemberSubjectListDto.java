package Refact.CalvinRefact.repository.dto.member;

import lombok.Data;

@Data
public class MemberSubjectListDto {


    private Long subject_id;
    private String subject_name;
    private String subject_field;
    private int fee;
    private Long Member_subject_id;
    private Long Member_id;
    private String pay_stat;


}
