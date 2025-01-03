package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.RolePermission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    @EntityGraph(attributePaths = {"permission"})
    List<RolePermission> findAllByRoleId(Long id);
    void deleteAllByRoleId(Long id);
}
