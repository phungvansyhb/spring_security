package lg.frontend.spring_security_section1.services.impl;

import lg.frontend.spring_security_section1.entities.Cloth;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.models.request.CreateCloth;
import lg.frontend.spring_security_section1.models.response.ClothListItem;
import lg.frontend.spring_security_section1.repositories.ClothRepository;
import lg.frontend.spring_security_section1.services.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClothServiceImpl implements ClothService {
    ClothRepository clothRepository;

    public ClothServiceImpl(@Autowired ClothRepository clothRepository) {
        this.clothRepository = clothRepository;
    }

    @Override
    public CustomResponse<List<ClothListItem>> search(String name, Integer price, Integer stock, LocalDateTime createdDate) {
        List<Cloth> cloths = clothRepository.findAllCloth(name, price, stock, createdDate);
        List<ClothListItem> clothListItems = new ArrayList<>();
        cloths.forEach(cloth -> {
            ClothListItem clothListItem = new ClothListItem();
            clothListItem.setId(cloth.getId());
            clothListItem.setImage(cloth.getImage());
            clothListItem.setName(cloth.getName());
            clothListItem.setPrice(cloth.getPrice());
            clothListItem.setStock(cloth.getStock());
            clothListItem.setCreatedDate(cloth.getCreatedDate());
            clothListItem.setUpdatedDate(cloth.getUpdatedDate());
            clothListItems.add(clothListItem);
        });
        return new CustomResponse<>(true, "ok", clothListItems);
    }

    @Override
    public CustomResponse<Cloth> getDetail(Long id) {
        Cloth cloth = clothRepository.findById(id).orElse(null);
        return new CustomResponse<>(true, "ok", cloth);
    }

    @Override
    public CustomResponse<Cloth> updateCloth(Long id, Cloth cloth) {
        Cloth oldCloth = clothRepository.findById(id).orElse(null);
        if(oldCloth != null) {
            oldCloth.setName(cloth.getName());
            oldCloth.setPrice(cloth.getPrice());
            oldCloth.setStock(cloth.getStock());
            oldCloth.setImage(cloth.getImage());
            oldCloth.setDescription(cloth.getDescription());
            clothRepository.save(oldCloth);
            return new CustomResponse<>(true, "ok", oldCloth);
        }else{
            return new CustomResponse<>(false, "Cloth not found", null);
        }
    }

    @Override
    public CustomResponse<Cloth> creteCloth(CreateCloth createCloth) {
        Cloth cloth = Cloth.builder()
                .name(createCloth.getName())
                .price(createCloth.getPrice())
                .description(createCloth.getDescription())
                .stock(createCloth.getStock())
                .build();
        Cloth createdCloth = clothRepository.save(cloth);
        return new CustomResponse<>(true, "ok", createdCloth);
    }

    @Override
    public CustomResponse<Cloth> deleteCloth(Long id) {
        Cloth oldCloth = clothRepository.findById(id).orElse(null);
        if(oldCloth != null) {
            clothRepository.deleteById(id);
            return new CustomResponse<>(true, "ok", oldCloth);
        }else{
            return new CustomResponse<>(false, "Cloth not found", null);
        }
    }
}
