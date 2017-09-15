package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.CategoryPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Viktor on 11-Aug-17.
 */
@Repository
public interface CategoryPictureRepository extends CrudRepository<CategoryPicture, Long> {
    CategoryPicture findByCategoryId(Long id);
}
