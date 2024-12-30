package lg.frontend.spring_security_section1.DTOs.request;


import lg.frontend.spring_security_section1.entities.Permission;
import lg.frontend.spring_security_section1.entities.User;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDTO {
    private String roleName;
    private String roleDescription;
    List<Permission> permissions;
    List<User> users;
}
