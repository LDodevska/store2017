package mk.ukim.finki.emt.service;


import mk.ukim.finki.emt.model.exceptions.CategoryInUseException;
import mk.ukim.finki.emt.model.jpa.*;

import java.sql.SQLException;

/**
 * @author Riste Stojanov
 */
public interface StoreManagementService {


  Category createTopLevelCategory(String name);

  Category createCategory(
    String name,
    Long parentId
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

  Product createProduct(
    String name,
    Long categoryId,
    String isbn,
    Double price
  );

  Product updateProduct(
    Long productId,
    String name,
    String isbn
  );

  Product updateProductPrice(
    Long productId,
    Double price
  );

  Product updateProductCategory(
    Long productId,
    Long newCategoryId
  );


  void addProductsInStock(
    Long productId,
    int quantity
  );

  void donateProducts(
    Long productId,
    int quantity
  );

  void clearCart(
    Long cartId
  );

  void markInvoiceAsExpired(
    Long invoiceId
  );

  DeliveryPackage markInvoiceAsPayed(
    Long invoiceId
  );

  void preparedDelivery(
    Long deliverId
  );

  void shippedDelivery(
    Long deliveryId
  );

  void closeDeliveryWithoutConfirmation(
    Long deliveryId
  );

  StorePicture createStorePicture();

  ProductDetails addProductDetails(Long productId, String details);

  ProductPicture addProductPicture(Long productId, byte[] bytes, String contentType) throws SQLException;

  StorePicture addStorePicture(byte[] bytes, String contentType) throws SQLException;

  CategoryPicture addCategoryPicture(Long categoryId, byte[] bytes, String contentType) throws SQLException;
}
