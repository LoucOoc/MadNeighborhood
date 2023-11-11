package tech.madneighborhood.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.madneighborhood.accounts.entity.Role;

@Repository("roles")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
