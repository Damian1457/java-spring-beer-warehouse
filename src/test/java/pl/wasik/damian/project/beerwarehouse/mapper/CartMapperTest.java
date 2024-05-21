package pl.wasik.damian.project.beerwarehouse.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartDto;

import java.util.Collections;
import java.util.List;

@DisplayName("CartMapper Test")
class CartMapperTest {

    private static final Long CART_ID = 1L;

    @Test
    @DisplayName("Map CartEntity to CartDto")
    void givenCartEntity_whenMapToDto_thenReturnCartDto() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);

        // when
        CartDto cartDto = cartMapper.toDto(cartEntity);

        // then
        Assertions.assertAll("cartDto",
                () -> Assertions.assertNotNull(cartDto, "CartDto should not be null"),
                () -> Assertions.assertEquals(CART_ID, cartDto.getId(), "CartDto ID should match CartEntity ID")
        );
    }

    @Test
    @DisplayName("Map CartDto to CartEntity")
    void givenCartDto_whenMapToEntity_thenReturnCartEntity() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);

        // when
        CartEntity cartEntity = cartMapper.toEntity(cartDto);

        // then
        Assertions.assertAll("cartEntity",
                () -> Assertions.assertNotNull(cartEntity, "CartEntity should not be null"),
                () -> Assertions.assertEquals(CART_ID, cartEntity.getId(), "CartEntity ID should match CartDto ID")
        );
    }

    @Test
    @DisplayName("Map List<CartEntity> to List<CartDto>")
    void givenCartEntityList_whenMapToDtoList_thenReturnCartDtoList() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(CART_ID);
        List<CartEntity> cartEntities = Collections.singletonList(cartEntity);

        // when
        List<CartDto> cartDtos = cartMapper.toDtoList(cartEntities);

        // then
        Assertions.assertAll("cartDtos",
                () -> Assertions.assertNotNull(cartDtos, "CartDtos list should not be null"),
                () -> Assertions.assertEquals(1, cartDtos.size(), "CartDtos list size should be 1"),
                () -> Assertions.assertEquals(CART_ID, cartDtos.get(0).getId(), "CartDto ID should match CartEntity ID")
        );
    }

    @Test
    @DisplayName("Map List<CartDto> to List<CartEntity>")
    void givenCartDtoList_whenMapToEntityList_thenReturnCartEntityList() {
        // given
        ModelMapper modelMapper = new ModelMapper();
        CartMapper cartMapper = new CartMapper(modelMapper);
        CartDto cartDto = new CartDto();
        cartDto.setId(CART_ID);
        List<CartDto> cartDtos = Collections.singletonList(cartDto);

        // when
        List<CartEntity> cartEntities = cartMapper.toEntityList(cartDtos);

        // then
        Assertions.assertAll("cartEntities",
                () -> Assertions.assertNotNull(cartEntities, "CartEntities list should not be null"),
                () -> Assertions.assertEquals(1, cartEntities.size(), "CartEntities list size should be 1"),
                () -> Assertions.assertEquals(CART_ID, cartEntities.get(0).getId(), "CartEntity ID should match CartDto ID")
        );
    }
}