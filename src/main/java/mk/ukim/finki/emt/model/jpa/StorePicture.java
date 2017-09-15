package mk.ukim.finki.emt.model.jpa;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "store_pictures")
public class StorePicture extends BaseEntity {

    @Embedded
    public FileEmbeddable picture;

}

