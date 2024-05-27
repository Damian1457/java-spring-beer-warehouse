package pl.wasik.damian.project.beerwarehouse.controller.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.wasik.damian.project.beerwarehouse.service.ProductService;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerMvcApplicationTest {

    public static final long PRODUCT_ID = 1L;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Should return products view")
    void listProducts() throws Exception {
        // Given

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andDo(print())
                .andExpect(content().string(containsString("Products")))
                .andExpect(status().isOk());

        //Then
    }

    @Test
    @DisplayName("Should return product view")
    void readView() throws Exception {
        // Given
        ProductDto productDto = new ProductDto();

        // When
        when(productService.read(PRODUCT_ID)).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", PRODUCT_ID))
                .andDo(print())
                .andExpect(content().string(containsString("Product Details")))
                .andExpect(status().isOk())
                .andExpect(view().name("products/read-product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", productDto));

        // Then
    }
}
