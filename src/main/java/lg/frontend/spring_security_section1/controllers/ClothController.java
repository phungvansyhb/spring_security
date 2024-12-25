package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.entities.Cloth;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.models.request.CreateCloth;
import lg.frontend.spring_security_section1.models.response.ClothListItem;
import lg.frontend.spring_security_section1.services.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cloth")
public class ClothController {
    @Autowired
    ClothService clothService;

    @GetMapping
    public CustomResponse<List<ClothListItem>> searchCloth(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) Integer price,
                                                           @RequestParam(required = false) Integer stock,
                                                           @RequestParam(required = false) LocalDateTime createdDate) {
        return clothService.search(name, price, stock, createdDate);
    }

    @GetMapping("/{id}")
    public CustomResponse<Cloth> getClothById(@PathVariable long id) {
        return clothService.getDetail(id);
    }

    @PutMapping("/{id}")
    public CustomResponse<Cloth> updateClothById(@PathVariable Long id , @RequestBody Cloth cloth) {
        return clothService.updateCloth(id, cloth);
    }

    @PostMapping
    public CustomResponse<Cloth> createCloth(@RequestBody CreateCloth createCloth) {
        return clothService.creteCloth(createCloth);
    }

    @DeleteMapping("/`{id}")
    public CustomResponse<Cloth> deleteCloth(@PathVariable long id) {
        return clothService.deleteCloth(id);
    }

}
