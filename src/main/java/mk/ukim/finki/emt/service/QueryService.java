package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.*;
import org.hibernate.search.annotations.Store;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Riste Stojanov
 */
public interface QueryService {

  Page<Product> getProductsInCategory(Long categoryId, int page, int pageSize);

  Page<Product> getPromotedProducts(int page, int pageSize);

  List<Category> findTopLevelCategories();

  Page<Category> findTopLevelCatPage();

  ProductPicture getByProductId(Long productId);

  CategoryPicture getByCategoryId(Long categoryId);

  StorePicture getByStorePictureId(Long storePictureId);

  Category getById(Long categoryId);

  List<Product> searchProduct(String query);


}
