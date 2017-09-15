package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.ProductDetails;
import org.springframework.data.repository.CrudRepository;

public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Long> {
    ProductDetails findByProductId(Long id);
}
