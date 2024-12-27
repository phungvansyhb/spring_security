package lg.frontend.spring_security_section1.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_log", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String method ;

    String ip;

    String time ;

    String apiPath ;

    @Column(columnDefinition = "TEXT", length = 1024)
    String parameters ;

    @Column(columnDefinition = "TEXT" , length = 1024)
    String body ;

    @Column(columnDefinition = "TEXT" , length = 1024)
    String response;
}
