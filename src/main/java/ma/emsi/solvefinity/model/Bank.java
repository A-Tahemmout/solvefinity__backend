package ma.emsi.solvefinity.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "bank")
public class Bank extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "slug")
    private String slug;

    @Column(name = "logo")
    private String logo;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private List<Loaner> loaners;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bank bank = (Bank) o;
        return getId() != null && Objects.equals(getId(), bank.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
