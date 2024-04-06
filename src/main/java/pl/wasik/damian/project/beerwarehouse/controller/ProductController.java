package pl.wasik.damian.project.beerwarehouse.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.wasik.damian.project.beerwarehouse.service.ProductService;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        LOGGER.info("listProducts()");
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        LOGGER.info("listProducts(...) = ");
        return "products/products-list";
    }

    @GetMapping("{id}")
    public String readView(@PathVariable Long id, Model model) {
        LOGGER.info("readView(" + id + ")");
        ProductDto readProductDto = productService.read(id);
        model.addAttribute("product", readProductDto);
        LOGGER.info("readView(...) = ");
        return "products/read-product";
    }

//    @PostMapping
//    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
//        if (file != null) {
//            Resource resource = file.getResource();
//
//            if (resource != null) {
//               resource.
//            }
//        }
//      ImageUploadResponse response = imageDataService.uploadImage(file);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(response);
//    }
}
