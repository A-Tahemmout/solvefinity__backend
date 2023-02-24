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
public class AuthenticationResponse {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private List<String> roles;
    private String accessToken;
}
