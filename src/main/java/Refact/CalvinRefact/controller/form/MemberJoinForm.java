package Refact.CalvinRefact.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberJoinForm {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String id;
    @NotEmpty(message = "아이디를 입력해주세요")
    private String id2;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String pwd;
    @NotEmpty(message = "이름을 입력해주세요")
    private String name;
    @NotNull(message = "생년월일을 입력해주세요")
    private LocalDate birth;
    @NotEmpty(message = "전화번호 입력해주세요")
    private String phone_number;
    @NotEmpty(message = "주소를 입력해주세요")
    private String address;
    private String address2;
}
