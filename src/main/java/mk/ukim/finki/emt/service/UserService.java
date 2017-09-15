package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.model.jpa.User;

import java.util.List;

/**
 * @author Riste Stojanov
 */
public interface UserService {

  User createCustomer(String username, String password, String email, ContactInfo contactInfo);

  User createAdmin(String username, String password);

  List<User> getAll();

  User getById(Long checkoutId);

  User updateUser(Long id, String username, String password, String email, ContactInfo contactInfo);

  public String encryptPassword(String password);

  public String decryptPassword(String password);

}
