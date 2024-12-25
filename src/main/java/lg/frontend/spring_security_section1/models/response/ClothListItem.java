package lg.frontend.spring_security_section1.models.response;


import lg.frontend.spring_security_section1.entities.BillItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClothListItem {
    private Long id;

    String name;

    Integer price;

    Integer stock;

    String image;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;
}
