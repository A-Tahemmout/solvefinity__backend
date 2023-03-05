package ma.emsi.solvefinity.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String cin;
    private String avatar;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;
    private List<String> roles;
    private String bank;
}
