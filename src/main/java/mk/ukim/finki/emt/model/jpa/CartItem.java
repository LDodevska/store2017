package mk.ukim.finki.emt.model.jpa;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Riste Stojanov
 */
@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

  @NotNull
  @Min(value = 1)
  public int quantity;

  @ManyToOne
  public Product product;

  @ManyToOne
  public Cart cart;
}
