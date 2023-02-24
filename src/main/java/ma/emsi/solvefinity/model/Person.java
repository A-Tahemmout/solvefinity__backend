package ma.emsi.solvefinity.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Person extends BaseEntity {
    @Column(name = "cin", unique = true)
    private String cin;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
