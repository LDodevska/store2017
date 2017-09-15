package mk.ukim.finki.emt.model.jpa;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Viktor on 07-Jun-17.
 */
@Entity
@Table(name = "product_pictures")
public class ProductPicture extends BaseEntity {

    @Embedded
    public FileEmbeddable picture;

    @ManyToOne
    public Product product;

}
