package pl.wasik.damian.project.beerwarehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartItemEntity;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartItemDto;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CartItemMapper {
    private static final Logger LOGGER = Logger.getLogger(CartItemMapper.class.getName());
    private final ModelMapper modelMapper;

    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartItemDto toDto(CartItemEntity cartItemEntity) {
        LOGGER.info("toDto(" + cartItemEntity + ")");
        CartItemDto cartItemDto = modelMapper.map(cartItemEntity, CartItemDto.class);
        ProductDto productDto = modelMapper.map(cartItemEntity.getProduct(), ProductDto.class);
        cartItemDto.setProduct(productDto);
        LOGGER.info("toDto(" + cartItemDto + ")");
        return cartItemDto;
    }

    public CartItemEntity toEntity(CartItemDto cartItemDto) {
        LOGGER.info("toEntity(" + cartItemDto + ")");
        CartItemEntity cartItemEntity = modelMapper.map(cartItemDto, CartItemEntity.class);
        ProductEntity productEntity = modelMapper.map(cartItemDto.getProduct(), ProductEntity.class);
        cartItemEntity.setProduct(productEntity);
        LOGGER.info("toEntity(...) = " + cartItemEntity);
        return cartItemEntity;
    }

    public List<CartItemDto> toDtoList(List<CartItemEntity> cartItems) {
        LOGGER.info("toDtoList(" + cartItems + ")");
        List<CartItemDto> collect = cartItems.stream().map(this::toDto).collect(Collectors.toList());
        LOGGER.info("toDtoList(...) = " + collect);
        return collect;
    }

    public List<CartItemEntity> toEntityList(List<CartItemDto> cartItemDtos) {
        LOGGER.info("toEntityList(" + cartItemDtos + ")");
        List<CartItemEntity> collect = cartItemDtos.stream().map(this::toEntity).collect(Collectors.toList());
        LOGGER.info("toEntityList(...) = " + collect);
        return collect;
    }
}