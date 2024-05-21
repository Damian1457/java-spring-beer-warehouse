package pl.wasik.damian.project.beerwarehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    private static final Logger LOGGER = Logger.getLogger(CartMapper.class.getName());
    private final ModelMapper modelMapper;

    public CartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartDto toDto(CartEntity cartEntity) {
        LOGGER.info("toDto(" + cartEntity + ")");
        CartDto mappedToDto = modelMapper.map(cartEntity, CartDto.class);
        LOGGER.info("toDto(" + mappedToDto + ")");
        return mappedToDto;
    }

    public CartEntity toEntity(CartDto cartDto) {
        LOGGER.info("toEntity(" + cartDto + ")");
        CartEntity mappedToEntity = modelMapper.map(cartDto, CartEntity.class);
        LOGGER.info("toEntity(...) = " + mappedToEntity);
        return mappedToEntity;
    }

    public List<CartDto> toDtoList(List<CartEntity> carts) {
        LOGGER.info("toDtoList(" + carts + ")");
        List<CartDto> collect = carts.stream().map(this::toDto).collect(Collectors.toList());
        LOGGER.info("toDtoList(...) = " + collect);
        return collect;
    }

    public List<CartEntity> toEntityList(List<CartDto> cartDto) {
        LOGGER.info("toEntityList(" + cartDto + ")");
        List<CartEntity> collect = cartDto.stream().map(this::toEntity).collect(Collectors.toList());
        LOGGER.info("toEntityList(...) = " + collect);
        return collect;
    }
}
