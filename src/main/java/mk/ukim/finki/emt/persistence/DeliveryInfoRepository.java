package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.DeliveryInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Viktor on 10-Apr-17.
 */
public interface DeliveryInfoRepository extends CrudRepository<DeliveryInfo, Long> {

}
