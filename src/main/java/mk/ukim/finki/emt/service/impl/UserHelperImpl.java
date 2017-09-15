package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.enums.UserType;
import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.model.jpa.User;
import mk.ukim.finki.emt.persistence.UserRepository;
import mk.ukim.finki.emt.service.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
@Service
public class UserHelperImpl implements UserHelper {
    private final UserRepository userRepository;

    @Autowired
    public UserHelperImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String username, String password, String email, UserType type, ContactInfo contactInfo) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.email = email;
        user.type = type;
        user.contactInfo = contactInfo;
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findOne(userId);
    }


}
