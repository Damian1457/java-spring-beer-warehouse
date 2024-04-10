package pl.wasik.damian.project.beerwarehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wasik.damian.project.beerwarehouse.repository.ProductRepository;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Product Service Integration Test")
class ProductServiceIntegrationTest {

    public static final String NAME = "Butter";
    public static final String UPDATED_NAME = "Milk";

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should find all products")
    void givenProductDto_whenFindAllProducts_thenCheckedProductsIsNotNull() {
        // Given
        ProductService productService = new ProductService(productRepository);
        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        byte[] imageBytes = new byte[]{1, 2, 3};

        // When
        productService.create(productDto, imageBytes);
        List<ProductDto> productsDto = productService.findAll();

        //Then
        Assertions.assertNotNull(productsDto, "Products should not be null");
    }

    @Test
    @DisplayName("Should read product by Id")
    void read() {
        // Given
        ProductService productService = new ProductService(productRepository);
        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        byte[] imageBytes = new byte[]{1, 2, 3};

        // When
        ProductDto createdProductDto = productService.create(productDto, imageBytes);
        ProductDto readProductDto = productService.read(createdProductDto.getId());

        //Then
        Assertions.assertEquals(createdProductDto.getId(), readProductDto.getId(), "Products Id should be the same");
    }

    @Test
    @DisplayName("Should update product name")
    void givenProductDto_whenUpdateName_thenProductNameIsUpdated() {
        // Given
        ProductService productService = new ProductService(productRepository);
        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        byte[] imageBytes = new byte[]{1, 2, 3};

        // When
        ProductDto createdProduct = productService.create(productDto, imageBytes);
        Long productId = createdProduct.getId();
        createdProduct.setName(UPDATED_NAME);
        productService.update(productId, createdProduct);
        ProductDto updatedProduct = productService.read(productId);

        // Then
        Assertions.assertEquals(UPDATED_NAME, updatedProduct.getName(), "Product name should be updated");
    }

    @Test
    @DisplayName("Should delete product")
    void giveProductDto_whenDeleteProductById_thenCheckedProductListSize() {
        // Given
        ProductService productService = new ProductService(productRepository);
        ProductDto productDto = new ProductDto();
        productDto.setName(NAME);
        byte[] imageBytes = new byte[]{1, 2, 3};

        // When
        ProductDto createProductDto = productService.create(productDto, imageBytes);
        productService.delete(createProductDto.getId());
        Optional<ProductEntity> productAfterDeletion = productRepository.findById(createProductDto.getId());

        // Then
        Assertions.assertTrue(productAfterDeletion.isEmpty(), "Product should not exist after deletion");
    }
}