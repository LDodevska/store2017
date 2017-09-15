package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.exceptions.DeliveryInfoInUseException;
import mk.ukim.finki.emt.model.jpa.DeliveryInfo;
import mk.ukim.finki.emt.model.jpa.User;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
public interface DeliveryInfoHelper {
    DeliveryInfo createDeliveryInfo(String country, String city, String postalCode, String address, User user);

    DeliveryInfo updateDeliveryInfoCountry(Long deliveryInfoID, String country);
    DeliveryInfo updateDeliveryInfoCity(Long deliveryInfoID, String city);
    DeliveryInfo updateDeliveryInfoPostalCode(Long deliveryInfoID, String postalCode);
    DeliveryInfo updateDeliveryInfoAddress(Long deliveryInfoID, String address);
    void removeDeliveryInfo(Long deliveryInfoID) throws DeliveryInfoInUseException;

    DeliveryInfo getById(Long id);
    DeliveryInfo getByUserId(Long userId);
    List<DeliveryInfo> getAll();
}
