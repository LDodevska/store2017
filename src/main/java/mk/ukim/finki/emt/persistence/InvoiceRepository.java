package mk.ukim.finki.emt.persistence;

import mk.ukim.finki.emt.model.jpa.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 14.3.2017.
 */
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice,Long> {
}
