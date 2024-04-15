package pl.wasik.damian.project.beerwarehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.beerwarehouse.repository.ProductRepository;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.util.Base64;

@SpringBootTest
@DisplayName("ProductService test")
class ProductServiceTest {

    public static final String NAME = "Bread";
    private static final byte[] IMAGE_BYTES = new byte[]{1, 2, 3};
    private static final String IMAGE_BASE64 = Base64.getEncoder().encodeToString(IMAGE_BYTES);

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should create product")
    void givenProductDto_whenSaveProduct_thenShouldBeSaved() {
        // Given
        ProductService productService = new ProductService(productRepository);
        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        productDto.setImageBase64(IMAGE_BASE64);


        // When
        ProductDto savedProductDto = productService.create(productDto);

        //Then
        Assertions.assertNotNull(savedProductDto, "Saved product should not be null");
    }
}