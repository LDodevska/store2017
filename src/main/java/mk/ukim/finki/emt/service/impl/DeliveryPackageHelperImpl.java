package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.enums.DeliveryStatus;
import mk.ukim.finki.emt.model.jpa.Checkout;
import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import mk.ukim.finki.emt.persistence.DeliveryPackageRepository;
import mk.ukim.finki.emt.service.DeliveryPackageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Simona on 04.04.2017.
 */
@Service
public class DeliveryPackageHelperImpl implements DeliveryPackageHelper {

    private final DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    public DeliveryPackageHelperImpl(DeliveryPackageRepository deliveryPackageRepository)
    {
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    @Override
    public DeliveryPackage createDeliveryPackage(Checkout checkout, DeliveryStatus status) {
        DeliveryPackage deliveryPackage = new DeliveryPackage();
        deliveryPackage.checkout = checkout;
        deliveryPackage.status = status;
        return deliveryPackageRepository.save(deliveryPackage);
    }
}
