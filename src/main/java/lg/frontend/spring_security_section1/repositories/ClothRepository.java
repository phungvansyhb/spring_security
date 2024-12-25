package lg.frontend.spring_security_section1.repositories;

import lg.frontend.spring_security_section1.entities.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {
    @Query( "SELECT c from Cloth c WHERE " +
            " (:name IS NULL or c.name LIKE %:name%) AND" +
            " (:price IS NULL or c.price = :price) AND" +
            " (:stock IS NULL or c.stock = :stock) AND" +
            " (cast(:createdDate as timestamp) IS NULL or c.createdDate = :createdDate) AND c.deletedDate IS NULL")
    List<Cloth> findAllCloth(@Param("name") String name,
                             @Param("price") Integer price,
                             @Param("stock") Integer stock,
                             @Param("createdDate") LocalDateTime createdDate);
}
