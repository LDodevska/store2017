package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.enums.DeliveryStatus;
import mk.ukim.finki.emt.model.jpa.Checkout;
import mk.ukim.finki.emt.model.jpa.DeliveryPackage;

/**
 * Created by Simona on 04.04.2017.
 */
public interface DeliveryPackageHelper {
    DeliveryPackage createDeliveryPackage(Checkout checkout,
                                          DeliveryStatus status);
}
