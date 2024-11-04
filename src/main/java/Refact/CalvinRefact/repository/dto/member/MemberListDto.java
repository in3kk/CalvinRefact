package Refact.CalvinRefact.repository.dto.member;

import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import lombok.Data;

@Data
public class MemberListDto {

    private Long id;
    private String email;
    private String name;
    private String phone_num;
    private Member_Type member_type;

    public MemberListDto(Long id, String email, String name, String phone_num, Member_Type member_type) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone_num = phone_num;
        this.member_type = member_type;
    }

    public MemberListDto() {
    }
}
