package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Simona on 04.04.2017.
 */
@Repository
public interface DeliveryPackageRepository extends CrudRepository<DeliveryPackage,Long>{
    DeliveryPackage findByCheckoutId(Long checkoutId);
}
