package pl.wasik.damian.project.beerwarehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.wasik.damian.project.beerwarehouse.mapper.CartItemMapper;
import pl.wasik.damian.project.beerwarehouse.mapper.CartMapper;
import pl.wasik.damian.project.beerwarehouse.repository.CartRepository;
import pl.wasik.damian.project.beerwarehouse.repository.ProductRepository;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartItemEntity;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartServiceTest {
    private static final Long CART_ID = 1L;
    private static final Long PRODUCT_ID = 2L;
    private static final int QUANTITY = 3;
    private static final String PRODUCT_DESCRIPTION = "Test Product Description";

    @Test
    @DisplayName("Get Cart")
    void givenCartId_whenGetCart_thenReturnCartDto() {
        // given
        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        CartMapper cartMapper = Mockito.mock(CartMapper.class);
        CartItemMapper cartItemMapper = Mockito.mock(CartItemMapper.class);
        CartService cartService = new CartService(cartRepository, productRepository, cartMapper, cartItemMapper);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cartEntity));
        when(cartMapper.toDto(cartEntity)).thenReturn(cartDto);

        // when
        CartDto result = cartService.getCart(CART_ID);

        // then
        Assertions.assertAll("cartDto",
                () -> Assertions.assertNotNull(result, "CartDto should not be null"),
                () -> Assertions.assertEquals(CART_ID, result.getId(), "CartDto ID should match CartEntity ID")
        );
    }

    @Test
    @DisplayName("Add Product to Cart")
    void givenCartIdAndProductIdAndQuantity_whenAddProductToCart_thenReturnUpdatedCartDto() {
        // given
        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        CartMapper cartMapper = Mockito.mock(CartMapper.class);
        CartItemMapper cartItemMapper = Mockito.mock(CartItemMapper.class);
        CartService cartService = new CartService(cartRepository, productRepository, cartMapper, cartItemMapper);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cartEntity));
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        when(cartRepository.save(any(CartEntity.class))).thenReturn(cartEntity);
        when(cartMapper.toDto(cartEntity)).thenReturn(cartDto);

        // when
        CartDto result = cartService.addProductToCart(CART_ID, PRODUCT_ID, QUANTITY);

        // then
        Assertions.assertAll("cartDto",
                () -> Assertions.assertNotNull(result, "CartDto should not be null"),
                () -> Assertions.assertEquals(CART_ID, result.getId(), "CartDto ID should match CartEntity ID")
        );
    }

    @Test
    @DisplayName("Remove Product from Cart")
    void givenCartIdAndProductId_whenRemoveProductFromCart_thenVerifyProductRemoved() {
        // given
        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        CartMapper cartMapper = Mockito.mock(CartMapper.class);
        CartItemMapper cartItemMapper = Mockito.mock(CartItemMapper.class);
        CartService cartService = new CartService(cartRepository, productRepository, cartMapper, cartItemMapper);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        CartItemEntity cartItemEntity = new CartItemEntity();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        cartItemEntity.setProduct(productEntity);
        cartEntity.getItems().add(cartItemEntity);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cartEntity));

        // when
        cartService.removeProductFromCart(CART_ID, PRODUCT_ID);

        // then
        Assertions.assertAll("cartEntity",
                () -> Assertions.assertTrue(cartEntity.getItems().isEmpty(), "Cart items should be empty")
        );
    }

    @Test
    @DisplayName("Clear Cart")
    void givenCartId_whenClearCart_thenVerifyCartCleared() {
        // given
        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        CartMapper cartMapper = Mockito.mock(CartMapper.class);
        CartItemMapper cartItemMapper = Mockito.mock(CartItemMapper.class);
        CartService cartService = new CartService(cartRepository, productRepository, cartMapper, cartItemMapper);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartEntity.getItems().add(cartItemEntity);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cartEntity));

        // when
        cartService.clearCart(CART_ID);

        // then
        Assertions.assertAll("cartEntity",
                () -> Assertions.assertTrue(cartEntity.getItems().isEmpty(), "Cart items should be empty")
        );
    }

    @Test
    @DisplayName("Update Product Quantity")
    void givenCartIdAndProductIdAndQuantity_whenUpdateProductQuantity_thenReturnUpdatedCartDto() {
        // given
        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        CartMapper cartMapper = Mockito.mock(CartMapper.class);
        CartItemMapper cartItemMapper = Mockito.mock(CartItemMapper.class);
        CartService cartService = new CartService(cartRepository, productRepository, cartMapper, cartItemMapper);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        CartItemEntity cartItemEntity = new CartItemEntity();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        cartItemEntity.setProduct(productEntity);
        cartEntity.getItems().add(cartItemEntity);
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);

        when(cartRepository.findById(CART_ID)).thenReturn(Optional.of(cartEntity));
        when(cartRepository.save(any(CartEntity.class))).thenReturn(cartEntity);
        when(cartMapper.toDto(cartEntity)).thenReturn(cartDto);

        // when
        CartDto result = cartService.updateProductQuantity(CART_ID, PRODUCT_ID, QUANTITY);

        // then
        Assertions.assertAll("cartDto",
                () -> Assertions.assertNotNull(result, "CartDto should not be null"),
                () -> Assertions.assertEquals(CART_ID, result.getId(), "CartDto ID should match CartEntity ID"),
                () -> Assertions.assertEquals(QUANTITY, cartItemEntity.getQuantity(), "CartItemEntity quantity should match updated quantity")
        );
    }
}