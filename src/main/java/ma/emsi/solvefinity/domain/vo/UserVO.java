package ma.emsi.solvefinity.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private UUID id;
    private Long version;

    private String cin;
    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    private LocalDate birthDate;

    private String username;
    private String password;
    private List<RoleVO> roles = new ArrayList<>();

    private BankVO bank;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
