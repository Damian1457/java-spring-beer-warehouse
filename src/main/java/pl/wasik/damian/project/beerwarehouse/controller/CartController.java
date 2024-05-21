package pl.wasik.damian.project.beerwarehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wasik.damian.project.beerwarehouse.service.CartService;
import pl.wasik.damian.project.beerwarehouse.web.model.CartDto;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public String getCart(@PathVariable Long cartId, Model model) {
        CartDto cart = cartService.getCart(cartId);
        model.addAttribute("cart", cart);
        return "cart/cart"; // Ścieżka do widoku, np. src/main/resources/templates/cart/cart-view.html
    }

    @GetMapping("/new")
    public String createCart(Model model) {
        CartDto cart = cartService.createCart();
        return "redirect:/cart/" + cart.getId(); // Przekierowanie do widoku koszyka
    }

    @PostMapping("/{cartId}/add")
    public String addProductToCart(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(cartId, productId, quantity);
        return "redirect:/cart/" + cartId; // Przekierowanie do widoku koszyka
    }

    @PostMapping("/{cartId}/remove")
    public String removeProductFromCart(@PathVariable Long cartId, @RequestParam Long productId) {
        cartService.removeProductFromCart(cartId, productId);
        return "redirect:/cart/" + cartId; // Przekierowanie do widoku koszyka
    }

    @PostMapping("/{cartId}/clear")
    public String clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return "redirect:/cart/" + cartId; // Przekierowanie do widoku koszyka
    }

    @PostMapping("/{cartId}/update")
    public String updateProductQuantity(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateProductQuantity(cartId, productId, quantity);
        return "redirect:/cart/" + cartId; // Przekierowanie do widoku koszyka
    }
}