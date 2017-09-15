package mk.ukim.finki.emt.web;

import com.sun.deploy.net.HttpResponse;
import mk.ukim.finki.emt.model.enums.UserType;
import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.service.*;
import mk.ukim.finki.emt.service.impl.NotificationServiceImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Riste Stojanov
 */
@Controller
public class PublicAccessController {


  QueryService queryService;
  UserService userService;
  DeliveryInfoHelper deliveryInfoHelper;
  ContactInfoHelper contactInfoHelper;
  NotificationServiceImpl notificationService;
  ProductServiceHelper productServiceHelper;


  @Autowired
  public PublicAccessController(QueryService queryService, UserService userService,
                                DeliveryInfoHelper deliveryInfoHelper, ContactInfoHelper contactInfoHelper,
                                NotificationServiceImpl notificationService,
                                ProductServiceHelper productServiceHelper) {
    this.deliveryInfoHelper = deliveryInfoHelper;
    this.contactInfoHelper = contactInfoHelper;
    this.queryService = queryService;
    this.userService = userService;
    this.notificationService = notificationService;
    this.productServiceHelper = productServiceHelper;
  }

  @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("products", productServiceHelper.getProductsInCategory(1l));

    return "index";
  }

  @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
  public String login(Model model, HttpSession session, @RequestParam(required = false) String error) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }
    model.addAttribute("error", error);
    model.addAttribute("pageFragment", "login");
    User user = (User) session.getAttribute("user");
    return "index";
  }

//  @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
//  public String logout(Model model, HttpSession session, @RequestParam(required = false) String error) {
//    if (session.getAttribute("user") != null) {
//      session.removeAttribute("user");
//    }
//    return "redirect:/login";
//  }

  @RequestMapping(value = {"/user/{userId}"}, method = RequestMethod.GET)
  public String getUser(
          @PathVariable Long userId,
          Model model,
          HttpSession session
  ) {
    User user = userService.getById(userId);
    User loggedUser = (User) session.getAttribute("user");
    if(loggedUser.id != user.id && loggedUser.type == UserType.ROLE_CUSTOMER) {
        return "redirect:/accessDenied";
    }
    //user & loggedUser
    model.addAttribute("user", loggedUser);
    model.addAttribute("pageFragment", "user");

    return "index";
  }

    @RequestMapping(value = {"/accessDenied"}, method = RequestMethod.GET)
    public String accessDenied(
            Model model
    ) {

        model.addAttribute("pageFragment", "accessDenied");
        return "index";
    }

    @RequestMapping(value = {"/category/{categoryId}"}, method = RequestMethod.GET)
  public String categoryProducts(
    @PathVariable Long categoryId,
    Model model
  ) {
    Page<Product> page = queryService.getProductsInCategory(
      categoryId, 0, 20
    );
    String categoryName = (queryService.getById(categoryId)).name;
    model.addAttribute("products", page);
    model.addAttribute("categoryName",categoryName);

    return "index";
  }

  @RequestMapping(value = {"/category"}, method = RequestMethod.GET)
  public String showCategories(
          Model model
  )
  {
    model.addAttribute("pageFragment", "categories");
    model.addAttribute("categories", queryService.findTopLevelCategories());

    return "index";
  }

  @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
  public String search(
    @RequestParam String query,
    Model model
  ) {
    List<Product> products = queryService.searchProduct(query);

    model.addAttribute("products", products);
    model.addAttribute("query", query);

    return "index";
  }

  @RequestMapping(value = {"/product/{id}"}, method = RequestMethod.GET)
  public String displayProduct(@PathVariable Long id, HttpServletResponse response, Model model) throws IOException, SQLException {
    model.addAttribute("pageFragment", "productDisplay");
    Product product = productServiceHelper.findById(id);
    model.addAttribute("prod", product);
      Page<Product> page = queryService.getProductsInCategory(
              product.category.id, 0, 20
      );
      String categoryName = (queryService.getById(product.category.id)).name;
      model.addAttribute("products", page);
      model.addAttribute("categoryName",categoryName);
      model.addAttribute("prodDesc", productServiceHelper.getProductDetails(id));
    return "index";
  }


  @RequestMapping(value = {"/product/{id}/picture"}, method = RequestMethod.GET)
  @ResponseBody
  public void index(@PathVariable Long id, HttpServletResponse response) throws IOException, SQLException {
    OutputStream out = response.getOutputStream();

    ProductPicture productPicture = queryService.getByProductId(id);

    String contentDisposition = String.format("inline;filename=\"%s\"",
      productPicture.picture.fileName + ".png?productId=" + id);

    response.setHeader("Content-Disposition", contentDisposition);
    response.setContentType(productPicture.picture.contentType);
    response.setContentLength(productPicture.picture.size);

    IOUtils.copy(productPicture.picture.data.getBinaryStream(), out);

    out.flush();
    out.close();
  }

  @RequestMapping(value = {"/category/{id}/picture"}, method = RequestMethod.GET)
  @ResponseBody
  public void indexCat(@PathVariable Long id, HttpServletResponse response) throws IOException, SQLException {
    OutputStream out = response.getOutputStream();

    CategoryPicture categoryPicture = queryService.getByCategoryId(id);

    String contentDisposition = String.format("inline;filename=\"%s\"",
            categoryPicture.picture.fileName + ".png?categoryId=" + id);

    response.setHeader("Content-Disposition", contentDisposition);
    response.setContentType(categoryPicture.picture.contentType);
    response.setContentLength(categoryPicture.picture.size);

    IOUtils.copy(categoryPicture.picture.data.getBinaryStream(), out);

    out.flush();
    out.close();
  }

    @RequestMapping(value = {"/aboutUs"}, method = RequestMethod.GET)
    public String aboutUs(Model model) {
        model.addAttribute("pageFragment", "aboutUs");
        return "index";
    }


    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("pageFragment", "home");
        return "index";
    }

    @RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
  public String registerUserTmp(Model model) {


      model.addAttribute("pageFragment", "registration");
      return "index";
  }

    @RequestMapping(value = {"/usernameAlreadyExists"}, method = RequestMethod.GET)
    public String usernameAlreadyExists(
            Model model
    ) {

        model.addAttribute("pageFragment", "usernameAlreadyExists");
        return "index";
    }

    @RequestMapping(value = {"/usernameAlreadyExists"}, method = RequestMethod.POST)
    public String usernameAlreadyExists(HttpServletRequest request,
                               HttpServletResponse resp,
                               Model model) {

        return "redirect:/registration";

    }

    @RequestMapping(value = {"/registration"}, method = RequestMethod.POST)
  public String registerUser(HttpServletRequest request,
                             HttpServletResponse resp,
                             Model model,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String phone,
                             @RequestParam String country,
                             @RequestParam String city,
                             @RequestParam String postalCode,
                             @RequestParam String address) {

    List<User> users = userService.getAll();
    Exception e = new Exception();
    Boolean flag = true;

      for(User u:users){
        if (u.username.equals(username))
//            model.addAttribute("exception", e);
            flag = false;
//          return "index";
            return "redirect:/usernameAlreadyExists";
    }

        ContactInfo contactInfo = contactInfoHelper.createContactInfo(firstName, lastName, phone);
        User user = userService.createCustomer(username, password, email, contactInfo);
        deliveryInfoHelper.createDeliveryInfo(country, city, postalCode, address, user);
        return "redirect:/login";


  }

    @RequestMapping(value = {"user/changePassword/{userId}"}, method = RequestMethod.GET)
    public String changePassword(@PathVariable Long userId,
                                 Model model,
                                 HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        User user = userService.getById(userId);
        if(loggedUser.id != user.id) {
            return "redirect:/accessDenied";
        }
        model.addAttribute("user",user);
        model.addAttribute("pageFragment", "changePassword");
        return "index";
    }

    @RequestMapping(value = {"user/changePassword/{userId}"}, method = RequestMethod.POST)
    public String changePassword(HttpServletRequest request,
                                    HttpServletResponse resp,
                                    HttpSession session,
                                    Model model,
                                    @RequestParam String newPassword,
                                    @RequestParam String currentPassword) {

        User user = (User) session.getAttribute("user");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(currentPassword,user.password)){
            String encPass = userService.encryptPassword(newPassword);
            User updatedUser = userService.updateUser(user.id,  user.username,encPass,user.email,contactInfoHelper.updateContactInfo(user.contactInfo.id, user.contactInfo.firstName,user.contactInfo.lastName,user.contactInfo.phone));
            DeliveryInfo deliveryInfo = deliveryInfoHelper.getByUserId(user.id);
            model.addAttribute("user", updatedUser);
            model.addAttribute("contactInfo",updatedUser.contactInfo);
            model.addAttribute("deliveryInfo", deliveryInfo);
            model.addAttribute("pageFragment", "updateUserProfile");
            return "index";
        }

        return "redirect:/accessDenied";

    }

    @RequestMapping(value = {"storePicture/{picId}"}, method = RequestMethod.GET)
    public String getStorePicture(@PathVariable Long picId,
                                 Model model,
                                 HttpServletResponse response,
                                 HttpSession session) throws IOException, SQLException {
        OutputStream out = response.getOutputStream();

        StorePicture storePicture = queryService.getByStorePictureId(picId);

        String contentDisposition = String.format("inline;filename=\"%s\"",
                storePicture.picture.fileName + ".png?storePicutreId=" + picId);

        response.setHeader("Content-Disposition", contentDisposition);
        response.setContentType(storePicture.picture.contentType);
        response.setContentLength(storePicture.picture.size);

        IOUtils.copy(storePicture.picture.data.getBinaryStream(), out);

        out.flush();
        out.close();

        return "index";
    }

    @RequestMapping(value = {"user/updateUserProfile/{userId}"}, method = RequestMethod.GET)
  public String getUserProfile(@PathVariable Long userId,
                               Model model,
                               HttpSession session) {
    User loggedUser = (User) session.getAttribute("user");
    User user = userService.getById(userId);
    if(loggedUser.id != user.id) {
          return "redirect:/accessDenied";
      }
    DeliveryInfo deliveryInfo = deliveryInfoHelper.getByUserId(user.id);
    model.addAttribute("user",user);
    model.addAttribute("contactInfo",user.contactInfo);
    model.addAttribute("deliveryInfo",deliveryInfo);
    model.addAttribute("pageFragment", "updateUserProfile");
    return "index";
  }

  @RequestMapping(value = {"user/updateUserProfile/{userId}"}, method = RequestMethod.POST)
  public String updateUserProfile(HttpServletRequest request,
                                   HttpServletResponse resp,
                                   HttpSession session,
                                   Model model,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String email,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String phone,
                                   @RequestParam String country,
                                   @RequestParam String city,
                                   @RequestParam String postalCode,
                                   @RequestParam String address,
                                   @RequestParam String currentPassword) {

      User user = (User) session.getAttribute("user");
      String encCurrentPassword = userService.encryptPassword(currentPassword);
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      if(encoder.matches(currentPassword,user.password)){
        User updatedUser = userService.updateUser(user.id,  username,user.password,email,contactInfoHelper.updateContactInfo(user.contactInfo.id, firstName,lastName,phone));
        DeliveryInfo deliveryInfo = deliveryInfoHelper.getByUserId(user.id);
        deliveryInfoHelper.updateDeliveryInfoAddress(deliveryInfo.id, address);
        deliveryInfoHelper.updateDeliveryInfoCity(deliveryInfo.id,city);
        deliveryInfoHelper.updateDeliveryInfoCountry(deliveryInfo.id,country);
        DeliveryInfo updatedDeliveryInfo = deliveryInfoHelper.updateDeliveryInfoPostalCode(deliveryInfo.id, postalCode);
        model.addAttribute("user", updatedUser);
        model.addAttribute("contactInfo",updatedUser.contactInfo);
        model.addAttribute("deliveryInfo", updatedDeliveryInfo);
        model.addAttribute("pageFragment", "updateUserProfile");
        return "index";
      }

      return "redirect:/accessDenied";

  }

  @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
  public String sendMessageTmp(Model model){
    model.addAttribute("pageFragment", "contact");
    return "index";
  }

  @RequestMapping(value = {"/contact"}, method = RequestMethod.POST)
  public String sendMessage(HttpServletRequest request,
                            HttpServletResponse resp,
                            Model model,
                            @RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String comment) {
      try {

          notificationService.receiveMessage(name, email, comment);
      } catch (MailException e) {

      }
      return "index";
  }

}