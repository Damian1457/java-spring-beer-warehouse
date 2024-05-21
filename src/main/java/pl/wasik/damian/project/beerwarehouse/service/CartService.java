package pl.wasik.damian.project.beerwarehouse.service;

import org.springframework.stereotype.Service;
import pl.wasik.damian.project.beerwarehouse.mapper.CartItemMapper;
import pl.wasik.damian.project.beerwarehouse.mapper.CartMapper;
import pl.wasik.damian.project.beerwarehouse.repository.CartRepository;
import pl.wasik.damian.project.beerwarehouse.repository.ProductRepository;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartEntity;
import pl.wasik.damian.project.beerwarehouse.repository.entity.CartItemEntity;
import pl.wasik.damian.project.beerwarehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.beerwarehouse.web.model.CartDto;

import java.util.logging.Logger;

@Service
public class CartService {
    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartMapper cartMapper, CartItemMapper cartItemMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
    }

    public CartDto getCart(Long cartId) {
        LOGGER.info("getCart(" + cartId + ")");
        CartDto cartNotFound = cartRepository.findById(cartId)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        LOGGER.info("getCart(...) = " + cartNotFound);
        return cartNotFound;
    }

    public CartDto createCart() {
        LOGGER.info("createCart()");
        CartEntity cart = new CartEntity();
        CartEntity savedCart = cartRepository.save(cart);
        CartDto cartDto = cartMapper.toDto(savedCart);
        LOGGER.info("createCart(...) = " + cartDto);
        return cartDto;
    }

    public CartDto addProductToCart(Long cartId, Long productId, int quantity) {
        LOGGER.info("addProductToCart(" + cartId + ", " + productId + ", " + quantity + ")");
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cart.getItems().add(cartItem);

        CartEntity updatedCart = cartRepository.save(cart);
        CartDto mappedToDto = cartMapper.toDto(updatedCart);
        LOGGER.info("addProductToCart(...) = " + mappedToDto);
        return mappedToDto;
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        LOGGER.info("removeProductFromCart(" + cartId + ", " + productId + ")");
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (!removed) {
            throw new RuntimeException("Product not found in cart");
        }

        cartRepository.save(cart);
        LOGGER.info("removeProductFromCart(...) = Product removed from cart");
    }

    public void clearCart(Long cartId) {
        LOGGER.info("clearCart()");
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
        LOGGER.info("clearCart(...) = Cart cleared");
    }

    public CartDto updateProductQuantity(Long cartId, Long productId, int quantity) {
        LOGGER.info("updateProductQuantity()");
        CartEntity cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItemEntity cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cartItem.setQuantity(quantity);
        CartEntity updatedCart = cartRepository.save(cart);
        CartDto mappedToDto = cartMapper.toDto(updatedCart);
        LOGGER.info("updateProductQuantity(...) = " + mappedToDto);
        return mappedToDto;
    }
}