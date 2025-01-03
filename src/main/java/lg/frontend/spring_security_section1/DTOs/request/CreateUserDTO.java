package lg.frontend.spring_security_section1.DTOs.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lg.frontend.spring_security_section1.models.Role;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotEmpty(message = "Name is required")
    @Min(value = 2, message = "Name must be at least 2 characters long")
    String username ;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    String email ;
    String phone ;
    String password ;
    String avatar ;
    Long balance;
}
