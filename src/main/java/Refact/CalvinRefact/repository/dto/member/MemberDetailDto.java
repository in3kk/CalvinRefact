package Refact.CalvinRefact.repository.dto.member;

import Refact.CalvinRefact.entity.embed.Address;
import Refact.CalvinRefact.entity.entityEnum.Member_Type;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberDetailDto {
    private Long id;
    private String username;
    private String email;
    private String phone_number;
    private Address address;
    private LocalDate birth;
    private LocalDateTime createdTime;
    private Member_Type member_type;

    public MemberDetailDto(Long id, String username, String email, String phone_number, Address address, LocalDate birth, LocalDateTime createdTime, Member_Type member_type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.birth = birth;
        this.createdTime = createdTime;
        this.member_type = member_type;
    }

    public MemberDetailDto() {
    }
}
