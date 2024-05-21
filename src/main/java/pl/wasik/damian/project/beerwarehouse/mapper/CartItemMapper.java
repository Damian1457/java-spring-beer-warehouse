package pl.wasik.damian.project.beerwarehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartItemEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartItemDto;

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
        CartItemDto mappedToDto = modelMapper.map(cartItemEntity, CartItemDto.class);
        mappedToDto.setProductDescription(cartItemEntity.getProduct().getDescription());;
        LOGGER.info("toDto(" + mappedToDto + ")");
        return mappedToDto;
    }

    public CartItemEntity toEntity(CartItemDto cartItemDto) {
        LOGGER.info("toEntity(" + cartItemDto + ")");
        CartItemEntity mappedToEntity = modelMapper.map(cartItemDto, CartItemEntity.class);
        LOGGER.info("toEntity(...) = " + mappedToEntity);
        return mappedToEntity;
    }

    public List<CartItemDto> toDtoList(List<CartItemEntity> cartItems) {
        LOGGER.info("toDtoList(" + cartItems + ")");
        List<CartItemDto> collect = cartItems.stream().map(this::toDto).collect(Collectors.toList());
        LOGGER.info("toDtoList(...) = " + collect);
        return collect;
    }

    public List<CartItemEntity> toEntityList(List<CartItemDto> cartItemDto) {
        LOGGER.info("toEntityList(" + cartItemDto + ")");
        List<CartItemEntity> collect = cartItemDto.stream().map(this::toEntity).collect(Collectors.toList());
        LOGGER.info("toEntityList(...) = " + collect);
        return collect;
    }
}
