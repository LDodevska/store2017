package mk.ukim.finki.emt.service.impl;

/**
 * Created by Viktor on 07-Jun-17.
 */

import mk.ukim.finki.emt.model.jpa.FileEmbeddable;
import mk.ukim.finki.emt.persistence.*;
import mk.ukim.finki.emt.service.ProductServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;

import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.persistence.CategoryRepository;

import java.util.List;

@Service
public class ProductHelperImpl implements ProductServiceHelper {

    /**
     * TODO: move this into book details helper
     */

    @Autowired
    ProductPictureRepository productPictureRepository;

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;



    @Autowired
    public ProductHelperImpl(
            CategoryRepository categoryRepository,
            ProductRepository bookRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = bookRepository;
    }

    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return null;
    }

    @Override
    public ProductDetails getProductDetails(Long productId) {
        return productDetailsRepository.findByProductId(productId);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(String name, Long categoryId, String isbn, Double price) {
        Product product = createNewProduct(name, categoryId, isbn, price);
        return productRepository.save(product);
    }


    @Override
    public Product updateProduct(Long productId, String name, String isbn) {
        return null;
    }

    @Override
    public Product updateProductPrice(Long productId, Double price) {
        return null;
    }

    @Override
    public Product updateProductCategory(Long productId, Long newCategoryId) {
        return null;
    }

    @Override
    public ProductPicture addProductPicture(Long productId, byte[] bytes, String contentType) throws SQLException {
        ProductPicture productPicture = new ProductPicture();
        productPicture.product = productRepository.findOne(productId);
        FileEmbeddable picture = new FileEmbeddable();
        picture.contentType = contentType;
        picture.data = new SerialBlob(bytes);
        picture.size = bytes.length;
        picture.fileName = productPicture.product.name;
        productPicture.picture = picture;
        return productPictureRepository.save(productPicture);
    }

    @Override
    public ProductDetails addProductDetails(Long productId, String description) {
        ProductDetails productDetails = new ProductDetails();
        productDetails.description = description;
        productDetails.product = productRepository.findOne(productId);
        return productDetailsRepository.save(productDetails);
    }


    private Product createNewProduct(String name, Long categoryId, String isbn, Double price) {
        Product product = new Product();
        product.name = name;
        product.isbn = isbn;
        product.price = price;
        product.category = categoryRepository.findOne(categoryId);
        return product;
    }
}

