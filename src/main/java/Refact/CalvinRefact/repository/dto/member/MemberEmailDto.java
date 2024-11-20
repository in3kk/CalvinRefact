package Refact.CalvinRefact.repository.dto.member;

import lombok.Data;

@Data
public class MemberEmailDto {
    private String email;
    private String name;
    private Long member_code;

    public MemberEmailDto(String email, String name, Long member_code) {
        this.email = email;
        this.name = name;
        this.member_code = member_code;
    }

    public MemberEmailDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public MemberEmailDto() {
    }
}
