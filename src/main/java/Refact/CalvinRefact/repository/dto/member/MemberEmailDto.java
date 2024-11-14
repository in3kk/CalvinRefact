package Refact.CalvinRefact.repository.dto.member;

import lombok.Data;

@Data
public class MemberEmailDto {
    private String email;
    private String name;

    public MemberEmailDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public MemberEmailDto() {
    }
}
