package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.Cart;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Simona on 04.04.2017.
 */
public interface CartHelper {
    Cart createCart(LocalDateTime expiryDate,
                    Double totalPrice);
    List<Cart> getAll();
    Cart getById(Long id);
}
