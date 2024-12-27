package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
