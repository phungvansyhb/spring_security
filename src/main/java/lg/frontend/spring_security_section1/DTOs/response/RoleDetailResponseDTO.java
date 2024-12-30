package lg.frontend.spring_security_section1.DTOs.response;

import lg.frontend.spring_security_section1.entities.Permission;
import lg.frontend.spring_security_section1.entities.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDetailResponseDTO {
    Long id;
    private String roleName;
    private String roleDescription;
    List<Permission> permissions;
    List<User> users;
    LocalDate createdDate;
    LocalDate updatedDate;

}
