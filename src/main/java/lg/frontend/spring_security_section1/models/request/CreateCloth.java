package lg.frontend.spring_security_section1.models.request;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCloth {
    String name;

    Integer price;

    Integer stock;

    String description;

    String image;

}
