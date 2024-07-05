package ua.eva.wims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.eva.wims.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByProductNameContainingIgnoreCase(String name);
    @Query("SELECT p FROM ProductEntity p WHERE p.productName LIKE %:name%")
    ProductEntity findByProductNameIgnoreCase(String name);

    @Query("SELECT p FROM ProductEntity p WHERE p.productName LIKE %:name% AND p.price BETWEEN :minPrice AND :maxPrice")
    List<ProductEntity> findByNameAndPriceRange(@Param("name") String name, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);
}
