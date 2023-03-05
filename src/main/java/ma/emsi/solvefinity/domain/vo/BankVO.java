package ma.emsi.solvefinity.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankVO {
    private UUID id;
    private Long version;

    private String name;
    private String description;
    private String slug;
    private String logo;
    private String email;
    private String phone;
    private String address;
    private String website;

    private Long usersCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
