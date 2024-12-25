package lg.frontend.spring_security_section1.DTOs.response;

import lg.frontend.spring_security_section1.entities.Cloth;
import lg.frontend.spring_security_section1.models.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDetail {
    private Long id;

    UserBill user;

    List<BillItem> billItems;

    Long price;

    PaymentMethod paymentMethod;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserBill {
        Long id;
        String name;
        String email;
        String avatar;
    }


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BillItem {
        Long id;
        Long clothId;
        String clothName;
        String clothImage;
        String clothDescription;
        Integer clothPrice;
        Integer quantity;
    }

}
