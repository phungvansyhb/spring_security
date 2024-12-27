package lg.frontend.spring_security_section1.DTOs.request;

import jakarta.persistence.Column;
import lombok.*;

public record LoginDTO(String username, String password) {
}