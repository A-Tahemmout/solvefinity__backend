package ma.emsi.solvefinity.repository;

import ma.emsi.solvefinity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    // Get only name field
    @Query("SELECT r.name FROM Role r ORDER BY r.name ASC")
    String[] findAllName();

    Role findByName(String name);

    List<Role> findByNameIn(List<String> names);
}
