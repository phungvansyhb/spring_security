package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByDeletedDateIsNull();
    Optional<Role> findByIdAndDeletedDateIsNull(Long id);
}
