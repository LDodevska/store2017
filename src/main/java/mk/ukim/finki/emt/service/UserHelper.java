package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.enums.UserType;
import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.model.jpa.User;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
public interface UserHelper {
    User createUser(String username, String password, String email, UserType type, ContactInfo contactInfo);

    List<User> getAll();

    User getById(Long checkoutId);
}