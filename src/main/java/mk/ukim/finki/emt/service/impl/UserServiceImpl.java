package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.enums.Provider;
import mk.ukim.finki.emt.model.enums.UserType;
import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.model.jpa.User;
import mk.ukim.finki.emt.persistence.ContactInfoRepository;
import mk.ukim.finki.emt.persistence.UserRepository;
import mk.ukim.finki.emt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Riste Stojanov
 */
@Service
public class UserServiceImpl implements UserService {

  private BCryptPasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  private Environment environment;
  private ContactInfoRepository contactInfoRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, Environment environment, ContactInfoRepository contactInfoRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.environment = environment;
    this.contactInfoRepository = contactInfoRepository;
  }

  @Override
  public User createCustomer(String username, String password, String email, ContactInfo contactInfo) {
    User user = new User();
    user.username = username;
    user.email = email;
    user.password = encryptPassword(password);
    user.type = UserType.ROLE_CUSTOMER;
    user.provider = Provider.LOCAL;
    user.contactInfo = contactInfo;
    return userRepository.save(user);

  }

  @Override
  public User createAdmin(String username, String password) {
    User user = new User();
    user.username = username;
    user.password = encryptPassword(password);
    user.type = UserType.ROLE_ADMIN;
    user.provider = Provider.LOCAL;

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

  @Override
  public User updateUser(Long id, String username, String password, String email, ContactInfo contactInfo) {
    User user = userRepository.findOne(id);
    user.username = username;
    user.password = password;
    //user.password = encryptPassword(password);
    user.email = email;
    user.contactInfo = contactInfo;

    ContactInfo contactInfo1 = contactInfoRepository.findOne(contactInfo.id);

    contactInfo1.firstName = contactInfo.firstName;
    contactInfo1.lastName = contactInfo.lastName;
    contactInfo1.phone = contactInfo.phone;

    contactInfoRepository.save(contactInfo1);
    return userRepository.save(user);
  }

  public String encryptPassword(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public String decryptPassword(String password) {
    return null;
  }


  @PostConstruct
  public void init() {
    if (environment.getProperty("store.security.defaultAdmin.create", Boolean.class, false)) {

      if (userRepository.count() == 0) {
        createAdmin(
          environment.getProperty("store.security.defaultAdmin.username"),
          environment.getProperty("store.security.defaultAdmin.password")
        );
      }
    }

  }
}
