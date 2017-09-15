package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.Cart;
import mk.ukim.finki.emt.model.jpa.Checkout;
import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.model.jpa.DeliveryInfo;
import mk.ukim.finki.emt.persistence.CheckoutRepository;
import mk.ukim.finki.emt.service.CheckoutHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Simona on 04.04.2017.
 */
@Service
public class CheckoutHelperImpl implements CheckoutHelper {
    private final CheckoutRepository checkoutRepository;

    @Autowired
    public CheckoutHelperImpl(CheckoutRepository checkoutRepository)
    {
        this.checkoutRepository = checkoutRepository;
    }

    @Override
    public Checkout createCheckout(Cart cart) {
        Checkout checkout = new Checkout();
        checkout.cart = cart;
        return checkoutRepository.save(checkout);
    }

    @Override
    public List<Checkout> getAll() {
        return (List<Checkout>) checkoutRepository.findAll();
    }

    @Override
    public Checkout getById(Long checkoutId) {
        return checkoutRepository.findOne(checkoutId);
    }

    @Override
    public void setDeliveryInfo(Long checkoutId, DeliveryInfo deliveryInfo) {
        Checkout checkout = checkoutRepository.findOne(checkoutId);
        checkout.deliveryInfo = deliveryInfo;
        checkoutRepository.save(checkout);
    }

    @Override
    public void setContactInfo(Long checkoutId, ContactInfo contactInfo) {
        Checkout checkout = checkoutRepository.findOne(checkoutId);
        checkout.contactInfo = contactInfo;
        checkoutRepository.save(checkout);
    }

}
