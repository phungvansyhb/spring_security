package lg.frontend.spring_security_section1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "permission_code", nullable = false, length = Integer.MAX_VALUE)
    private String permissionCode;

    @Column(name = "permission_description", length = Integer.MAX_VALUE)
    private String permissionDescription;

}