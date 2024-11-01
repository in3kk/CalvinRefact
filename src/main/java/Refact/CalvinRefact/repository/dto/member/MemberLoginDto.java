package Refact.CalvinRefact.repository.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginDto {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String id;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String pwd;

    public MemberLoginDto(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public MemberLoginDto() {
    }
}
