package lg.frontend.spring_security_section1.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BillItem", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cloth_id")
    Cloth cloth;

    int quantity;

    @ManyToOne()
    @JoinColumn(name="bill_id")
    Bill bill;
}
