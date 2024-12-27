package lg.frontend.spring_security_section1.DTOs.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {
    String method ;
    String ip;
    String time ;
    String apiPath ;
    String parameters ;
    String body ;
    String response;
}
