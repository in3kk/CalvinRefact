package Refact.CalvinRefact.repository.dto.member;

import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import lombok.Data;

@Data
public class MemberListDto {

    private Long id;
    private String email;
    private String name;
    private String phone_number;
    private Member_Type member_type;

    public MemberListDto(Long id, String email, String name, String phone_number, Member_Type member_type) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone_number = phone_number;
        this.member_type = member_type;
    }

    public MemberListDto() {
    }
}
