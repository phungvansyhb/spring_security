package lg.frontend.spring_security_section1.DTOs.request;

import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleDTO {
    private String roleName;
    private String roleDescription;
    List<Long> permissionIds;
}
