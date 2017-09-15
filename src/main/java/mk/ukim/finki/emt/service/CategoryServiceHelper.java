package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.exceptions.CategoryInUseException;
import mk.ukim.finki.emt.model.jpa.Category;
import mk.ukim.finki.emt.model.jpa.CategoryPicture;
import mk.ukim.finki.emt.model.jpa.ProductPicture;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Riste Stojanov
 */
public interface CategoryServiceHelper {

  List<Category> getTopLevelCategories();

  List<Category> getSubCategories(Long categoryId);

  Category createTopLevelCategory(String name);

  Category createCategory(
    String name,
    long parentId
  );

  Category updateCategoryName(
    Long id,
    String newName
  );

  Category changeCategoryParent(
    Long id,
    Long parentId
  );

  void removeCategory(Long id)
    throws CategoryInUseException;

  CategoryPicture addCategoryPicture(
          Long categoryId,
          byte[] bytes,
          String contentType) throws SQLException;

}
