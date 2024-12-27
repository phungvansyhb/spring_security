package lg.frontend.spring_security_section1.DTOs.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserListResponse {
    private Long id;
    private String username ;
    private String email ;
    private String password ;
    private String phone ;
    private String avatar ;
    private String role ;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
