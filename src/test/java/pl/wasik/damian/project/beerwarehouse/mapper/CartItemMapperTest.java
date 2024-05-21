package pl.wasik.damian.project.beerwarehouse.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartItemEntity;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartItemDto;

import java.util.Collections;
import java.util.List;

@DisplayName("CartItemMapper Test")
class CartItemMapperTest {

    private static final Long CART_ITEM_ID = 1L;
    private static final Long PRODUCT_ID = 2L;
    private static final String PRODUCT_DESCRIPTION = "Sample product description";

    @Test
    @DisplayName("Map CartItemEntity to CartItemDto")
    void givenCartItemEntity_whenMapToDto_thenReturnCartItemDto() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartItemMapper cartItemMapper = new CartItemMapper(modelMapper);
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(CART_ITEM_ID);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        cartItemEntity.setProduct(productEntity);

        // when
        CartItemDto cartItemDto = cartItemMapper.toDto(cartItemEntity);

        // then
        Assertions.assertAll("cartItemDto",
                () -> Assertions.assertNotNull(cartItemDto),
                () -> Assertions.assertEquals(CART_ITEM_ID, cartItemDto.getId()),
                () -> Assertions.assertEquals(PRODUCT_DESCRIPTION, cartItemDto.getProductDescription())
        );
    }

    @Test
    @DisplayName("Map CartItemDto to CartItemEntity")
    void givenCartItemDto_whenMapToEntity_thenReturnCartItemEntity() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartItemMapper cartItemMapper = new CartItemMapper(modelMapper);
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(CART_ITEM_ID);
        cartItemDto.setProductId(PRODUCT_ID);
        cartItemDto.setProductDescription(PRODUCT_DESCRIPTION);

        // when
        CartItemEntity cartItemEntity = cartItemMapper.toEntity(cartItemDto);

        // then
        Assertions.assertAll("cartItemEntity",
                () -> Assertions.assertNotNull(cartItemEntity),
                () -> Assertions.assertEquals(CART_ITEM_ID, cartItemEntity.getId())
        );
    }

    @Test
    @DisplayName("Map List<CartItemEntity> to List<CartItemDto>")
    void givenCartItemEntityList_whenMapToDtoList_thenReturnCartItemDtoList() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartItemMapper cartItemMapper = new CartItemMapper(modelMapper);
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setId(CART_ITEM_ID);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        cartItemEntity.setProduct(productEntity);
        List<CartItemEntity> cartItemEntities = Collections.singletonList(cartItemEntity);

        // when
        List<CartItemDto> cartItemDtos = cartItemMapper.toDtoList(cartItemEntities);

        // then
        Assertions.assertAll("cartItemDtos",
                () -> Assertions.assertNotNull(cartItemDtos),
                () -> Assertions.assertEquals(1, cartItemDtos.size()),
                () -> Assertions.assertEquals(CART_ITEM_ID, cartItemDtos.get(0).getId()),
                () -> Assertions.assertEquals(PRODUCT_DESCRIPTION, cartItemDtos.get(0).getProductDescription())
        );
    }

    @Test
    @DisplayName("Map List<CartItemDto> to List<CartItemEntity>")
    void givenCartItemDtoList_whenMapToEntityList_thenReturnCartItemEntityList() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartItemMapper cartItemMapper = new CartItemMapper(modelMapper);
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(CART_ITEM_ID);
        cartItemDto.setProductId(PRODUCT_ID);
        cartItemDto.setProductDescription(PRODUCT_DESCRIPTION);
        List<CartItemDto> cartItemDtos = Collections.singletonList(cartItemDto);

        // when
        List<CartItemEntity> cartItemEntities = cartItemMapper.toEntityList(cartItemDtos);

        // then
        Assertions.assertAll("cartItemEntities",
                () -> Assertions.assertNotNull(cartItemEntities),
                () -> Assertions.assertEquals(1, cartItemEntities.size()),
                () -> Assertions.assertEquals(CART_ITEM_ID, cartItemEntities.get(0).getId())
        );
    }
}