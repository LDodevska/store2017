package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.ProductPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Viktor on 07-Jun-17.
 */
@Repository
public interface ProductPictureRepository extends CrudRepository<ProductPicture, Long> {
    ProductPicture findByProductId(Long id);
}

