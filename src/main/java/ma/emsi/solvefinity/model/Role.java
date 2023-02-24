package ma.emsi.solvefinity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
