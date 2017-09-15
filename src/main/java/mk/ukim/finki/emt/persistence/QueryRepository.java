package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.Product;
import org.springframework.data.domain.Page;

/**
 * @author Riste Stojanov
 */
public interface QueryRepository {

  Page<Product> findProductsByCategoryPaged(Long categoryId, int page, int pageSize);

  Page<Product> findPromotedProducts(int page, int pageSize);
}
