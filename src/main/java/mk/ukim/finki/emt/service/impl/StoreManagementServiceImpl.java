package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.exceptions.CategoryInUseException;
import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.persistence.StoreRepository;
import mk.ukim.finki.emt.service.CategoryServiceHelper;
import mk.ukim.finki.emt.service.ProductServiceHelper;
import mk.ukim.finki.emt.service.StoreManagementService;
import mk.ukim.finki.emt.service.StorePictureHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;

/**
 * @author Riste Stojanov
 */
@Service
public class StoreManagementServiceImpl implements StoreManagementService {

  @Autowired
  private CategoryServiceHelper categoryServiceHelper;

  @Autowired
  private ProductServiceHelper productServiceHelper;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private StorePictureHelper storePictureHelper;

  @Override
  public Category createTopLevelCategory(String name) {
    return categoryServiceHelper.createTopLevelCategory(name);
  }

  @Override
  public Category createCategory(String name, Long parentId) {
    return categoryServiceHelper.createCategory(name, parentId);
  }

  @Override
  public Category updateCategoryName(Long id, String newName) {
    return categoryServiceHelper.updateCategoryName(id, newName);
  }

  @Override
  public Category changeCategoryParent(Long id, Long parentId) {
    return categoryServiceHelper.changeCategoryParent(id, parentId);
  }

  @Override
  public void removeCategory(Long id) throws CategoryInUseException {
    categoryServiceHelper.removeCategory(id);
  }

  @Override
  public Product createProduct(String name, Long categoryId,
                             String isbn, Double price) {
    return productServiceHelper.createProduct(name, categoryId, isbn, price);
  }

  @Override
  public Product updateProduct(Long productId, String name, String isbn) {
    return productServiceHelper.updateProduct(productId, name, isbn);
  }

  @Override
  public Product updateProductPrice(Long productId, Double price) {
    return productServiceHelper.updateProductPrice(productId, price);
  }

  @Override
  public Product updateProductCategory(Long productId, Long newCategoryId) {
    return productServiceHelper.updateProductCategory(productId, newCategoryId);
  }

  @Override
  public void addProductsInStock(Long productId, int quantity) {

  }

  @Override
  public void donateProducts(Long productId, int quantity) {

  }

  @Override
  public void clearCart(Long cartId) {

  }

  @Override
  public void markInvoiceAsExpired(Long invoiceId) {

  }

  @Override
  public DeliveryPackage markInvoiceAsPayed(Long invoiceId) {
    return null;
  }

  @Override
  public void preparedDelivery(Long deliverId) {

  }

  @Override
  public void shippedDelivery(Long deliveryId) {

  }

  @Override
  public void closeDeliveryWithoutConfirmation(Long deliveryId) {

  }

    @Override
    public StorePicture createStorePicture() {
        return null;
    }

    @Override
  public ProductDetails addProductDetails(Long productId, String details) {
    return productServiceHelper.addProductDetails(productId,details);
  }

  @Override
  public ProductPicture addProductPicture(Long productId, byte[] bytes, String contentType) throws SQLException {
    return productServiceHelper.addProductPicture(productId, bytes, contentType);
  }

  @Override
  public StorePicture addStorePicture(byte[] bytes, String contentType) throws SQLException {
    FileEmbeddable picture = new FileEmbeddable();
    picture.contentType = contentType;
    picture.data = new SerialBlob(bytes);
    picture.size = bytes.length;
    StorePicture picture1 = new StorePicture();
    picture1.picture=picture;
//    picture.fileName = productPicture.product.name;
//    productPicture.picture = picture;
    return storeRepository.save(picture1);
  }

  @Override
  public CategoryPicture addCategoryPicture(Long categoryId, byte[] bytes, String contentType) throws SQLException {
    return categoryServiceHelper.addCategoryPicture(categoryId, bytes, contentType);
  }
}
