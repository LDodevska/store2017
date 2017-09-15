package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.DeliveryInfo;
import mk.ukim.finki.emt.model.jpa.User;
import mk.ukim.finki.emt.persistence.DeliveryInfoRepository;
import mk.ukim.finki.emt.service.DeliveryInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
@Service
public class DeliveryInfoHelperImpl implements DeliveryInfoHelper {

    private final DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    public DeliveryInfoHelperImpl(DeliveryInfoRepository deliveryInfoRepository)
    {
        this.deliveryInfoRepository = deliveryInfoRepository;
    }


    @Override
    public DeliveryInfo createDeliveryInfo(String country, String city, String postalCode, String address, User customer) {
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.country = country;
        deliveryInfo.city = city;
        deliveryInfo.postalCode = postalCode;
        deliveryInfo.address = address;
        deliveryInfo.customer = customer;
        return deliveryInfoRepository.save(deliveryInfo);
    }

    @Override
    public DeliveryInfo updateDeliveryInfoCountry(Long deliveryInfoID, String country) {
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findOne(deliveryInfoID);
        deliveryInfo.country = country;
        return deliveryInfoRepository.save(deliveryInfo);
    }

    @Override
    public DeliveryInfo updateDeliveryInfoCity(Long deliveryInfoID, String city) {
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findOne(deliveryInfoID);
        deliveryInfo.city = city;
        return deliveryInfoRepository.save(deliveryInfo);
    }

    @Override
    public DeliveryInfo updateDeliveryInfoPostalCode(Long deliveryInfoID, String postalCode) {
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findOne(deliveryInfoID);
        deliveryInfo.postalCode = postalCode;
        return deliveryInfoRepository.save(deliveryInfo);
    }

    @Override
    public DeliveryInfo updateDeliveryInfoAddress(Long deliveryInfoID, String address) {
        DeliveryInfo deliveryInfo = deliveryInfoRepository.findOne(deliveryInfoID);
        deliveryInfo.address = address;
        return deliveryInfoRepository.save(deliveryInfo);
    }

    @Override
    public void removeDeliveryInfo(Long deliveryInfoID) {
        deliveryInfoRepository.delete(deliveryInfoID);
    }

    @Override
    public DeliveryInfo getById(Long id) {
        return deliveryInfoRepository.findOne(id);
    }

    @Override
    public DeliveryInfo getByUserId(Long userId) {
        Iterable<DeliveryInfo> deliveryInfos = deliveryInfoRepository.findAll();
        for(DeliveryInfo df: deliveryInfos){
            if (df.customer.id == userId)
                return df;
        }

        return null;
    }

    @Override
    public List<DeliveryInfo> getAll() {
        return (List<DeliveryInfo>) deliveryInfoRepository.findAll();
    }
}
