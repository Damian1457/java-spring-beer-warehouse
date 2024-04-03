package pl.wasik.damian.project.beerwarehouse.repository.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ProductEntity class test suite")
class ProductEntityTest {

    public static final String NAME = "Test";

    @Test
    @DisplayName("Given product entity when set capacity then capacity is not null")
    void givenProductEntity_whenSetId_thenIdIsNotNull() {
        // Given
        ProductEntity productEntity = new ProductEntity();

        // When
        productEntity.setName(NAME);

        //Then
        Assertions.assertNotNull(productEntity.getName(), "The name is NULL");
    }
}