package pl.wasik.damian.project.beerwarehouse.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;

@SpringBootTest
class ProductRepositoryTest {

    public static final String NAME = "Milk";
    @Autowired
    private ProductRepository productRepository;

    @Test
    void givenProductEntity_whenSaveEntityToRepository_thenCheckedNameIsNotNull() {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(NAME);

        // When
        productRepository.save(productEntity);

        //Then
        Assertions.assertNotNull(productEntity.getName(), "The name is NULL");
    }
}