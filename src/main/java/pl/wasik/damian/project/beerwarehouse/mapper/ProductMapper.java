package pl.wasik.damian.project.beerwarehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;


@Component
public class ProductMapper {

    private static final Logger LOGGER = Logger.getLogger(ProductMapper.class.getName());
    private static final ModelMapper modelMapper = new ModelMapper();

    public static List<ProductDto> mapToDtoList(List<ProductEntity> products) {
        LOGGER.info("mapToDtoList(" + products + ")");
        List<ProductDto> mappedToProductsDto = products.stream()
                .map(ProductMapper::mapToDto)
                .toList();
        LOGGER.info("mapToDtoList(...) = " + mappedToProductsDto);
        return mappedToProductsDto;
    }

    public static ProductDto mapToDto(ProductEntity productEntity) {
        ProductDto dto = modelMapper.map(productEntity, ProductDto.class);
        if (productEntity.getImage() != null) {
            dto.setImageBase64(Base64.getEncoder().encodeToString(productEntity.getImage()));
        }
        return dto;
    }

    public static ProductEntity mapToEntity(ProductDto productDto) {
        ProductEntity entity = modelMapper.map(productDto, ProductEntity.class);
        if (productDto.getImageBase64() != null && !productDto.getImageBase64().isEmpty()) {
            entity.setImage(Base64.getDecoder().decode(productDto.getImageBase64()));
        }
        return entity;
    }
}