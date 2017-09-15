package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.persistence.*;
import mk.ukim.finki.emt.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Riste Stojanov
 */
@Service
public class QueryServiceImpl implements QueryService {

  QueryRepository queryRepository;

  CategoryRepository categoryRepository;

  ProductPictureRepository productPictureRepository;

  CategoryPictureRepository categoryPictureRepository;

  StoreRepository storePictureRepository;

  @Autowired
  SearchRepository searchRepository;

  @Autowired
  public QueryServiceImpl(
    QueryRepository bookRepository,
    CategoryRepository categoryRepository,
    ProductPictureRepository productPictureRepository,
    CategoryPictureRepository categoryPictureRepository,
    StoreRepository storePictureRepository
  ) {
    this.queryRepository = bookRepository;
    this.categoryRepository = categoryRepository;
    this.productPictureRepository = productPictureRepository;
    this.categoryPictureRepository = categoryPictureRepository;
    this.storePictureRepository = storePictureRepository;
  }

  @Override
  public Page<Product> getProductsInCategory(Long categoryId, int page, int pageSize) {
    return queryRepository.findProductsByCategoryPaged(categoryId, page, pageSize);
  }

  @Override
  public Page<Product> getPromotedProducts(int page, int pageSize) {
    return queryRepository.findPromotedProducts(page, pageSize);

  }



  @Override
  public List<Category> findTopLevelCategories() {
    return categoryRepository.findByParentIsNull();
  }

  @Override
  public Page<Category> findTopLevelCatPage() {
    return null;
  }

  @Override
  public ProductPicture getByProductId(Long bookId) {
    return productPictureRepository.findByProductId(bookId);
  }

  @Override
  public CategoryPicture getByCategoryId(Long categoryId) {
    return categoryPictureRepository.findByCategoryId(categoryId);
  }

  @Override
  public StorePicture getByStorePictureId(Long storePictureId) {
    return storePictureRepository.findById(storePictureId);
  }

  @Override
  public Category getById(Long categoryId) {
    return categoryRepository.findOne(categoryId);
  }

  @Override
  public List<Product> searchProduct(String query) {
    return searchRepository.searchPhrase(
            Product.class,
      query,
      "name", "isbn", "category.name");
  }
}
