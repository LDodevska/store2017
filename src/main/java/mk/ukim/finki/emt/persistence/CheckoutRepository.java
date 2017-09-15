package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.Checkout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Simona on 04.04.2017.
 */
@Repository
public interface CheckoutRepository extends CrudRepository<Checkout,Long>{
}
