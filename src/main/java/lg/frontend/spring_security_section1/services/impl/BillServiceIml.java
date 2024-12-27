package lg.frontend.spring_security_section1.services.impl;

import jakarta.transaction.Transactional;
import lg.frontend.spring_security_section1.DTOs.response.BillDetail;
import lg.frontend.spring_security_section1.entities.Bill;
import lg.frontend.spring_security_section1.entities.BillItem;
import lg.frontend.spring_security_section1.entities.Cloth;
import lg.frontend.spring_security_section1.entities.User;
import lg.frontend.spring_security_section1.exceptions.ItemQuantityException;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.models.request.CreateBill;
import lg.frontend.spring_security_section1.repositories.BillRepository;
import lg.frontend.spring_security_section1.repositories.ClothRepository;
import lg.frontend.spring_security_section1.repositories.UserRepository;
import lg.frontend.spring_security_section1.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BillServiceIml implements BillService {
    BillRepository billRepository;
    UserRepository userRepository;
    ClothRepository clothRepository;

    public BillServiceIml(@Autowired BillRepository billRepository, @Autowired UserRepository userRepository, ClothRepository clothRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.clothRepository = clothRepository;
    }

    @Override
    @Transactional
    public CustomResponse<Bill> createBill(CreateBill createBill) {
        Bill bill = new Bill();
        User user = userRepository.findById(createBill.getUserId()).orElse(null);

        bill.setUser(user);
        bill.setPaymentMethod(createBill.getPaymentMethod());
        Bill createdBill = billRepository.save(bill);

        List<BillItem> billItemList = new ArrayList<>();
        AtomicReference<Long> price = new AtomicReference<>(0L);
        createBill.getBillItemList().forEach(item -> {
            BillItem billItem = new BillItem();
            Cloth cloth = clothRepository.findById(item.getClothId()).orElse(null);
            billItem.setCloth(cloth);
            billItem.setBill(createdBill);
            Integer stockQuantity = null;
            if (cloth != null) {
                stockQuantity = cloth.getStock();
                if( stockQuantity  < item.getQuantity()){
                    throw new ItemQuantityException("Quantity large than stock");
                }
                price.updateAndGet(v -> v + cloth.getPrice());
                billItem.setQuantity(item.getQuantity());
                billItemList.add(billItem);
            }
        });
        createdBill.setBillItems(billItemList);
        createdBill.setPrice(price.get());

        billRepository.save(createdBill);


        return new CustomResponse<>(true, "ok", createdBill);
    }

    public BillDetail findBillDetailById(Long id){
        Bill bill = billRepository.findById(id).orElse(null);
        if(bill != null){
            BillDetail.UserBill userBill = new BillDetail.UserBill();
            userBill.setId(bill.getUser().getId());
            userBill.setName(bill.getUser().getUsername());
            userBill.setAvatar(bill.getUser().getAvatar());
            userBill.setEmail(bill.getUser().getEmail());

            List<BillDetail.BillItem> billItems = new ArrayList<>();
            bill.getBillItems().forEach(item->{
                BillDetail.BillItem billItem = new BillDetail.BillItem();
                billItem.setId(item.getId());
                billItem.setClothId(item.getCloth().getId());
                billItem.setClothName(item.getCloth().getName());
                billItem.setClothImage(item.getCloth().getImage());
                billItem.setClothDescription(item.getCloth().getDescription());
                billItem.setClothPrice(item.getCloth().getPrice());
                billItem.setQuantity(item.getQuantity());
                billItems.add(billItem);
            });

            return BillDetail.builder().id(bill.getId())
                    .user(userBill)
                    .paymentMethod(bill.getPaymentMethod())
                    .price(bill.getPrice())
                    .billItems(billItems)
                    .createdDate(bill.getCreatedDate())
                    .updatedDate(bill.getUpdatedDate())
                    .build();
        }
        return null;
    }

    @Override
    public CustomResponse<BillDetail> getBillById(Long id) {
        BillDetail result = findBillDetailById(id);
        return new CustomResponse<>(true,"ok", result);
    }

    @Override
    public CustomResponse<List<BillDetail>> getBillByUserId(Long id) {
        List<Bill> bills = billRepository.findAllByUserId(id);
        List<BillDetail> billDetails = new ArrayList<>();
        bills.forEach(item -> {
            BillDetail billDetail = findBillDetailById(item.getId());
            billDetails.add(billDetail);
        });
        return new CustomResponse<>(true,"ok", billDetails);
    }
}
