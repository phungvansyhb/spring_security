package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
