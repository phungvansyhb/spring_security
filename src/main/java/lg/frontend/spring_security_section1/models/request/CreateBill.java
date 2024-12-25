package lg.frontend.spring_security_section1.models.request;

import lg.frontend.spring_security_section1.models.PaymentMethod;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBill {
    Long userId;

    PaymentMethod paymentMethod;

    List<CreateBillItem> billItemList;

}
