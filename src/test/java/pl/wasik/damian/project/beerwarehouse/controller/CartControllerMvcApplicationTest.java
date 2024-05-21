package pl.wasik.damian.project.beerwarehouse.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasik.damian.project.beerwarehouse.service.CartService;
import pl.wasik.damian.project.beerwarehouse.web.model.CartDto;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    private static final Long CART_ID = 1L;
    private static final Long PRODUCT_ID = 2L;
    private static final int QUANTITY = 3;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("Get Cart - Success")
    void givenCartId_whenGetCart_thenReturnCartView() throws Exception {
        // given
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setItems(Collections.emptyList());

        when(cartService.getCart(anyLong())).thenReturn(cartDto);

        // when & then
        mockMvc.perform(get("/cart/{cartId}", CART_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attribute("cart", cartDto));
    }

    @Test
    @DisplayName("Create Cart - Redirect")
    void whenCreateCart_thenRedirectToCartView() throws Exception {
        // given
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setItems(Collections.emptyList());

        when(cartService.createCart()).thenReturn(cartDto);

        // when & then
        mockMvc.perform(get("/cart/new"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/" + CART_ID));
    }

    @Test
    @DisplayName("Add Product to Cart - Redirect")
    void givenCartIdAndProductIdAndQuantity_whenAddProductToCart_thenRedirectToCartView() throws Exception {
        // given
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setItems(Collections.emptyList());

        when(cartService.addProductToCart(anyLong(), anyLong(), anyInt())).thenReturn(cartDto);

        // when & then
        mockMvc.perform(post("/cart/{cartId}/add", CART_ID)
                        .param("productId", PRODUCT_ID.toString())
                        .param("quantity", String.valueOf(QUANTITY)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/" + CART_ID));
    }

    @Test
    @DisplayName("Remove Product from Cart - Redirect")
    void givenCartIdAndProductId_whenRemoveProductFromCart_thenRedirectToCartView() throws Exception {
        // given
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setItems(Collections.emptyList());

        Mockito.doNothing().when(cartService).removeProductFromCart(anyLong(), anyLong());

        // when & then
        mockMvc.perform(post("/cart/{cartId}/remove", CART_ID)
                        .param("productId", PRODUCT_ID.toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/" + CART_ID));
    }

    @Test
    @DisplayName("Clear Cart - Redirect")
    void givenCartId_whenClearCart_thenRedirectToCartView() throws Exception {
        // given
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setItems(Collections.emptyList());

        Mockito.doNothing().when(cartService).clearCart(anyLong());

        // when & then
        mockMvc.perform(post("/cart/{cartId}/clear", CART_ID))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/" + CART_ID));
    }

    @Test
    @DisplayName("Update Product Quantity - Redirect")
    void givenCartIdAndProductIdAndQuantity_whenUpdateProductQuantity_thenRedirectToCartView() throws Exception {
        // given
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        cartDto.setItems(Collections.emptyList());

        when(cartService.updateProductQuantity(anyLong(), anyLong(), anyInt())).thenReturn(cartDto);

        // when & then
        mockMvc.perform(post("/cart/{cartId}/update", CART_ID)
                        .param("productId", PRODUCT_ID.toString())
                        .param("quantity", String.valueOf(QUANTITY)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/" + CART_ID));
    }
}