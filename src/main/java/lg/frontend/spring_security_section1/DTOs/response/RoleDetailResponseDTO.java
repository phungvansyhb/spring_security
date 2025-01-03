package lg.frontend.spring_security_section1.DTOs.response;

import lg.frontend.spring_security_section1.entities.Permission;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        Long id;
        String name ;
        String email ;
        String avatar ;

    }
}
