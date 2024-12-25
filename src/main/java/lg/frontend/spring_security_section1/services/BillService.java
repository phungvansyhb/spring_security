package lg.frontend.spring_security_section1.services;

import lg.frontend.spring_security_section1.DTOs.response.BillDetail;
import lg.frontend.spring_security_section1.entities.Bill;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.models.request.CreateBill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillService {
    public CustomResponse<Bill> createBill(CreateBill createBill);

    public CustomResponse<BillDetail> getBillById(Long id);

    public CustomResponse<List<BillDetail>> getBillByUserId(Long id);
}
