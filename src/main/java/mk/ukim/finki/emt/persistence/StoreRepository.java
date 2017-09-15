package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.FileEmbeddable;
import mk.ukim.finki.emt.model.jpa.ProductPicture;
import mk.ukim.finki.emt.model.jpa.StorePicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<StorePicture, Long> {
    StorePicture findById(Long id);
}
