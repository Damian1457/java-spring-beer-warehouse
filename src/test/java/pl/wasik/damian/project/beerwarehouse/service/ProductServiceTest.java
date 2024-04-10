package pl.wasik.damian.project.beerwarehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.beerwarehouse.repository.ProductRepository;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

@SpringBootTest
@DisplayName("ProductService test")
class ProductServiceTest {

    public static final String NAME = "Bread";
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should create product")
    void givenProductEntity_whenSaveEntity_thenShouldBeSaved() {
        // Given
        ProductService productService = new ProductService(productRepository);
        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        byte[] imageBytes = new byte[]{1, 2, 3};

        // When
        ProductDto savedProductDto = productService.create(productDto, imageBytes);

        //Then
        Assertions.assertNotNull(savedProductDto, "Saved product should not be null");
    }
}