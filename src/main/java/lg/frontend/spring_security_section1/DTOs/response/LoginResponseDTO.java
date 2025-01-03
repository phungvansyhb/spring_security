package lg.frontend.spring_security_section1.DTOs.response;

import lg.frontend.spring_security_section1.entities.Permission;
import lg.frontend.spring_security_section1.entities.Role;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String username;

    private String phone;

    private String email;

    private String avatar;

    private Long balance;

    private List<Role> roleList;

    private List<Permission> permissionList;

    private String token;

}

