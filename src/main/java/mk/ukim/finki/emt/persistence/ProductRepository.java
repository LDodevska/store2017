package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.Product;
import mk.ukim.finki.emt.model.jpa.ProductPicture;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Viktor on 07-Jun-17.
 */
public interface ProductRepository extends CrudRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Product findById(Long id);
}
