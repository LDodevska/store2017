package mk.ukim.finki.emt.service;

import com.sun.corba.se.pept.transport.ContactInfo;
import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.model.search.ProductSearchCriteria;

import java.util.List;


/**
 * @author Riste Stojanov
 */
public interface StoreClientService {

  Cart takeCart();

  List<Product> findProducts(ProductSearchCriteria criteria);

  List<Category> getTopLevelCategories();

  List<Category> getSubCategories(Long categoryId);

  List<Product> getProductsInCategory(Long categoryId);

  ProductDetails getProductDetails(Long productId);

  CartItem addToCart(Long cartId, Long productId, int quantity);

  void removeFromCart(Long cartId, Long productId, int quantity);

  Checkout startCheckout(Long cartId);


  DeliveryInfo provideDeliveryInfo(
    Long checkoutId,
    String country,
    String city,
    String postalCode,
    String address);

  ContactInfo provideContactInfo(
    Long checkoutId,
    String firstName,
    String lastName,
    String phone
  );

  void provideDeliveryAndContactInfoFromCustomerCard(
    Long checkoutId,
    Long customerId
  );

  void provideCoupon(
    Long checkoutId,
    String coupon
  );

  Invoice providePaymentInfo(
    Long checkoutId,
    String cardNumber,
    String cardHolder,
    String cardType,
    String cvs,
    String expiryDate
  );


  void confirmDelivery(
    Long invoiceId,
    Integer rating,
    String comment
  );

}
