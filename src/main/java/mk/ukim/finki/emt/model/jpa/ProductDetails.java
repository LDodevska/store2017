package mk.ukim.finki.emt.model.jpa;

import javax.persistence.*;

/**
 * Created by Viktor on 07-Jun-17.
 */
@Entity
@Table(name = "product_details")
public class ProductDetails extends BaseEntity {

    @Column(length = 5000)
    public String description;

    @OneToOne
    public Product product;

//    @Embedded
//    public FileEmbeddable downloadFile;


}