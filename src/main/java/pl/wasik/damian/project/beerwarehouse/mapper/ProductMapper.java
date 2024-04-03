package pl.wasik.damian.project.beerwarehouse.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

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
        LOGGER.info("mapToDto(" + productEntity + ")");
        ProductDto mapperToProductDto = modelMapper.map(productEntity, ProductDto.class);
        LOGGER.info("mapToDto(...) = " + mapperToProductDto);
        return mapperToProductDto;
    }

    public static ProductEntity mapToEntity(ProductDto productDto) {
        LOGGER.info("mapToEntity(" + productDto + ")");
        ProductEntity mappedToProductEntity = modelMapper.map(productDto, ProductEntity.class);
        LOGGER.info("mapToEntity(...) = " + mappedToProductEntity);
        return mappedToProductEntity;
    }
}
