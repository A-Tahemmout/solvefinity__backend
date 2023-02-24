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
public class RoleVO {
    private UUID id;
    private Long version;

    private String name;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
