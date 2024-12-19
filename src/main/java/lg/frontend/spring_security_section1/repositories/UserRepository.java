package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDeletedDateIsNull();
    List<User> findAllByNameAndEmail(String name, String email);
    List<User> findAllByNameLikeAndEmailLike(String name, String email);
}
