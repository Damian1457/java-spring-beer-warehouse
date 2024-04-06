package pl.wasik.damian.project.beerwarehouse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wasik.damian.project.beerwarehouse.mapper.ProductMapper;
import pl.wasik.damian.project.beerwarehouse.repository.ProductRepository;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        LOGGER.info("findAll()");
        List<ProductDto> productsDto = ProductMapper.mapToDtoList(productRepository.findAll());
        LOGGER.info("findAll(...) = " + productsDto);
        return productsDto;
    }

    public ProductDto create(ProductDto productDto) {
        LOGGER.info("create()");
        ProductEntity mappedToEntity = ProductMapper.mapToEntity(productDto);
        ProductDto savedProductDto = ProductMapper.mapToDto(productRepository.save(mappedToEntity));
        LOGGER.info("create(...) = " + savedProductDto);
        return savedProductDto;
    }

    public ProductDto read(Long id) {
        LOGGER.info("read(" + id + ")");
        ProductDto productDto = ProductMapper.mapToDto(productRepository.findById(id).orElseThrow());
        LOGGER.info("read(...) = " + productDto);
        return productDto;
    }

    public ProductDto update(Long id, ProductDto productDto) {
        LOGGER.info("update()");
        ProductEntity mappedProductEntity = ProductMapper.mapToEntity(productDto);
        mappedProductEntity.setId(id);
        ProductEntity updatedProductEntity = productRepository.save(mappedProductEntity);
        ProductDto mappedProductDto = ProductMapper.mapToDto(updatedProductEntity);
        LOGGER.info("update(...) = " + mappedProductDto);
        return mappedProductDto;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        productRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }

    public ProductDto uploadImage(Long id, MultipartFile file) throws IOException {
        LOGGER.info("uploadImage(" + id + ")");
        ProductEntity productEntity = productRepository.findById(id).orElseThrow();
        productEntity.setImage(file.getBytes());
        ProductEntity updatedProductEntity = productRepository.save(productEntity);
        ProductDto mappedToDto = ProductMapper.mapToDto(updatedProductEntity);
        LOGGER.info("uploadImage(...) = ");
        return mappedToDto;
    }
}
