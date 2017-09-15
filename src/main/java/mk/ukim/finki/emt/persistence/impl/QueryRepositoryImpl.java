package mk.ukim.finki.emt.persistence.impl;

import mk.ukim.finki.emt.model.jpa.Product;
import mk.ukim.finki.emt.persistence.ProductRepository;
import mk.ukim.finki.emt.persistence.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * @author Riste Stojanov
 */
@Repository
public class QueryRepositoryImpl implements QueryRepository {

  ProductRepository productRepository;

  @Autowired
  public QueryRepositoryImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  @Override
  public Page<Product> findProductsByCategoryPaged(Long categoryId, int page, int pageSize) {
    return productRepository.findAll(
      (book, cq, cb) -> cb.equal(book.join("category").get("id"), categoryId),
      new PageRequest(page, pageSize)
    );
  }

  @Override
  public Page<Product> findPromotedProducts(int page, int pageSize) {
    return productRepository.findAll(
      (book, cq, cb) -> cb.equal(book.get("promoted"), true),
      new PageRequest(page, pageSize)
    );
  }
}
