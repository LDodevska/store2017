package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.Cart;
import mk.ukim.finki.emt.model.jpa.Checkout;
import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.model.jpa.DeliveryInfo;

import java.util.List;

/**
 * Created by Simona on 04.04.2017.
 */
public interface CheckoutHelper {
    Checkout createCheckout(Cart cart);
    List<Checkout> getAll();

    Checkout getById(Long checkoutId);

    void setDeliveryInfo(Long checkoutId, DeliveryInfo deliveryInfo);
    void setContactInfo(Long checkoutId, ContactInfo contactInfo);
}
