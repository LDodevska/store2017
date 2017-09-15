package mk.ukim.finki.emt.model.jpa;

import javax.persistence.*;

/**
 * Created by Viktor on 11-Aug-17.
 */
@Entity
@Table(name = "category_pictures")
public class CategoryPicture extends BaseEntity {

    @Embedded
    public FileEmbeddable picture;

    @OneToOne
    public Category category;

}

