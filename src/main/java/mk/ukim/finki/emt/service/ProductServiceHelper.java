package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Viktor on 07-Jun-17.
 */
public interface ProductServiceHelper {

    List<Product> getProductsInCategory(Long categoryId);

    ProductDetails getProductDetails(Long productId);

    Product findById(Long id);

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
            Long bookId,
            Double price
    );

    Product updateProductCategory(
            Long productId,
            Long newCategoryId
    );


    ProductPicture addProductPicture(
            Long productId,
            byte[] bytes,
            String contentType) throws SQLException;

    ProductDetails addProductDetails(Long productId, String description);
}

