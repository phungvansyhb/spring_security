package lg.frontend.spring_security_section1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "role_name", nullable = false, length = Integer.MAX_VALUE)
    private String roleName;

    @Column(name = "role_description", length = Integer.MAX_VALUE)
    private String roleDescription;

    @ColumnDefault("now()")
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDate createdDate;

    @ColumnDefault("now()")
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDate updatedDate;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;

}