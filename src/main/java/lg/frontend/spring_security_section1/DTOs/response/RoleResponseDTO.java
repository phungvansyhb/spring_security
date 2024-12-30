package lg.frontend.spring_security_section1.DTOs.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {
    Long id;
    private String roleName;
    private String roleDescription;
    List<Long> permissionId;
    LocalDate createdDate;
    LocalDate updatedDate;
}
