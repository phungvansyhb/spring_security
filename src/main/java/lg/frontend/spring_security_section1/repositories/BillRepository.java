package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.Bill;
import lg.frontend.spring_security_section1.entities.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findAllByUserId(Long userId);
}
