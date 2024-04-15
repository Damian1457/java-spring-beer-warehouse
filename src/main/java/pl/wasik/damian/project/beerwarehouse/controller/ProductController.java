package pl.wasik.damian.project.beerwarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wasik.damian.project.beerwarehouse.service.ProductService;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.io.IOException;
import java.util.Base64;
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

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView(" + model + ")");
        model.addAttribute("productDto", new ProductDto());
        LOGGER.info("createView(...) = ");
        return "products/create-product";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProductDto productDto, @RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            if (image != null && !image.isEmpty()) {
                String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());
                productDto.setImageBase64(imageBase64);
            }
            productService.create(productDto);
            redirectAttributes.addFlashAttribute("successMessage", "Produkt został dodany pomyślnie.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Błąd przetwarzania obrazu: " + e.getMessage());
            return "redirect:/products/create";
        }
        return "redirect:/products";
    }
}



