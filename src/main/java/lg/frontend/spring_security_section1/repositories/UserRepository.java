package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUsernameLikeAndEmailLike(String name, String email);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}
