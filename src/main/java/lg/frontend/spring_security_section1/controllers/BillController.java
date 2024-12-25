package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.DTOs.response.BillDetail;
import lg.frontend.spring_security_section1.entities.Bill;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.models.request.CreateBill;
import lg.frontend.spring_security_section1.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping
    public CustomResponse<Bill> createBill(@RequestBody CreateBill createBill) {
        return billService.createBill(createBill);
    }

    @GetMapping("/{id}")
    public CustomResponse<BillDetail> getBill(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    @GetMapping("/user/{id}")
    public CustomResponse<List<BillDetail>> getBillByUserId(@PathVariable Long id) {
        return billService.getBillByUserId(id);
    }
}
