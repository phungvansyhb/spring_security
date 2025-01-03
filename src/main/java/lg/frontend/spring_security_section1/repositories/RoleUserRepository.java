package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.RoleUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<RoleUser> findAllByRoleId(Long id);
    @EntityGraph(attributePaths = {"role"})
    List<RoleUser> findByUserId(Long id);
    void deleteAllByRoleId(Long id);
}
