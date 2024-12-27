package lg.frontend.spring_security_section1.DTOs.response;

import lombok.*;

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

    private String role;

    private String token;

}

