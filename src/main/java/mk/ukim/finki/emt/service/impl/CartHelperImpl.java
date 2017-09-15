package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.Cart;
import mk.ukim.finki.emt.persistence.CartRepository;
import mk.ukim.finki.emt.service.CartHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Simona on 04.04.2017.
 */
@Service
public class CartHelperImpl implements CartHelper {

    private final CartRepository cartRepository;

    @Autowired
    CartHelperImpl(CartRepository cartRepository)
    {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(LocalDateTime expiryDate, Double totalPrice) {
        Cart cart = new Cart();
        cart.expiryDate = expiryDate;
        cart.totalPrice = totalPrice;
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.findOne(id);
    }
}
