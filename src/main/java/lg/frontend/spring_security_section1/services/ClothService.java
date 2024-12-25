package lg.frontend.spring_security_section1.services;

import lg.frontend.spring_security_section1.entities.Cloth;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.models.request.CreateCloth;
import lg.frontend.spring_security_section1.models.response.ClothListItem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public interface ClothService {
    CustomResponse<List<ClothListItem>> search(String name , Integer price , Integer stock , LocalDateTime createdDate);

    CustomResponse<Cloth> getDetail(Long id);

    CustomResponse<Cloth> updateCloth(Long id , Cloth cloth);

    CustomResponse<Cloth> creteCloth(CreateCloth createCloth);

    CustomResponse<Cloth> deleteCloth(Long id);
}
