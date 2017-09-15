package mk.ukim.finki.emt.web;

import mk.ukim.finki.emt.model.jpa.*;
import mk.ukim.finki.emt.service.*;
import mk.ukim.finki.emt.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Riste Stojanov
 */
@Controller
public class AdminController {

  StoreManagementService storeManagementService;
  UserService userService;
  DeliveryInfoHelper deliveryInfoHelper;
  CheckoutHelper checkoutHelper;
  InvoiceHelper invoiceHelper;
  NotificationServiceImpl notificationService;


  @Autowired
  public AdminController(StoreManagementService storeManagementService, UserService userService, DeliveryInfoHelper deliveryInfoHelper,
                         CheckoutHelper checkoutHelper, InvoiceHelper invoiceHelper, NotificationServiceImpl notificationService) {
    this.storeManagementService = storeManagementService;
    this.deliveryInfoHelper = deliveryInfoHelper;
    this.userService = userService;
    this.checkoutHelper = checkoutHelper;
    this.invoiceHelper = invoiceHelper;
    this.notificationService = notificationService;
  }

  @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
  public String admin(Model model) {
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    if(authentication.isAuthenticated()) {
      UserDetails details= (UserDetails) authentication.getPrincipal();
    }
    model.addAttribute("pageFragment", "admin");
    return "index";
  }

  @RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
  public String addCategory(Model model) {
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    if(authentication.isAuthenticated()) {
      UserDetails details= (UserDetails) authentication.getPrincipal();
    }
    model.addAttribute("pageFragment", "addCategory");
    return "index";
  }

  @RequestMapping(value = {"/admin/product"}, method = RequestMethod.GET)
  public String addProduct(Model model) {
    model.addAttribute("pageFragment", "addProduct");
    return "index";
  }


  @RequestMapping(value = {"/admin/category"}, method = RequestMethod.POST)
  public String createCategory(Model model,
                               @RequestParam String categoryName,
                                MultipartFile picture) throws IOException, SQLException {
    Category category = storeManagementService.createTopLevelCategory(categoryName);
    storeManagementService.addCategoryPicture(category.id, picture.getBytes(), picture.getContentType());


    model.addAttribute("category", category);
    return "redirect:/admin/category";
  }


  @RequestMapping(value = {"/admin/addStorePicture"}, method = RequestMethod.GET)
  public String addStorePicture(Model model) {
    model.addAttribute("pageFragment", "addStorePicture");
    return "index";
  }

  @RequestMapping(value = {"/admin/addStorePicture"}, method = RequestMethod.POST)
  public String addStorePicture(Model model,
                              MultipartFile picture) throws IOException, SQLException {

    storeManagementService.addStorePicture(picture.getBytes(), picture.getContentType());

//    model.addAttribute("product", product);
    model.addAttribute("pageFragment", "addStorePicture");
    return "index";
  }



  @RequestMapping(value = {"/admin/product"}, method = RequestMethod.POST)
  public String createProduct(Model model,
                              @RequestParam String name,
                              @RequestParam Long categoryId,
                              @RequestParam String isbn,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam String description,
                              MultipartFile picture) throws IOException, SQLException {

    Product product = storeManagementService.createProduct(
      name,
      categoryId,
      isbn,
      price
    );

    product.quantityInStock = quantity;
    storeManagementService.addProductPicture(product.id, picture.getBytes(), picture.getContentType());
    storeManagementService.addProductDetails(product.id, description);

    model.addAttribute("product", product);
    model.addAttribute("pageFragment", "addProduct");
    return "index";
  }

  @RequestMapping(value = {"/deliveryInfo"}, method = RequestMethod.GET)
  public String addDeliveryInfo(Model model) {
    List<User> users = userService.getAll();
    model.addAttribute("users", users);
    model.addAttribute("delivery_info", deliveryInfoHelper.getAll());
    model.addAttribute("pageFragment", "addDeliveryInfo");
    return "index";
  }

  @RequestMapping(value = {"/deliveryInfo"}, method = RequestMethod.POST)
  public String createDeliveryInfo(HttpServletRequest request,
                                   HttpServletResponse resp,
                                   Model model,
                                   @RequestParam Long userId,
                                   @RequestParam String country,
                                   @RequestParam String city,
                                   @RequestParam String postalCode,
                                   @RequestParam String address) {

    User user = userService.getById(userId);

    DeliveryInfo deliveryInfo = deliveryInfoHelper.createDeliveryInfo(country, city, postalCode, address, user);
    return "redirect:/deliveryInfo";
  }

  @RequestMapping(value = {"/deliveryInfo/updateCountry"}, method = RequestMethod.POST)
  public String updateCountry(Model model,@RequestParam Long id, @RequestParam String newCountry) {
    deliveryInfoHelper.updateDeliveryInfoCountry(id, newCountry);
    return "redirect:/deliveryInfo";
  }

  @RequestMapping(value = {"/deliveryInfo/updateCity"}, method = RequestMethod.POST)
  public String updateCity(Model model, @RequestParam Long id, @RequestParam String newCity) {
    deliveryInfoHelper.updateDeliveryInfoCity(id, newCity);
    return "redirect:/deliveryInfo";
  }

  @RequestMapping(value = {"/deliveryInfo/updatePostalCode"}, method = RequestMethod.POST)
  public String updatePostalCode(Model model, @RequestParam Long id, @RequestParam String newPostalCode) {
    deliveryInfoHelper.updateDeliveryInfoPostalCode(id, newPostalCode);
    return "redirect:/deliveryInfo";
  }

  @RequestMapping(value = {"/deliveryInfo/updateAddress"}, method = RequestMethod.POST)
  public String updateAddress(Model model, @RequestParam Long id, @RequestParam String newAddress) {
    deliveryInfoHelper.updateDeliveryInfoAddress(id, newAddress);
    return "redirect:/deliveryInfo";
  }

  @RequestMapping(value = {"/admin/invoice"}, method = RequestMethod.GET)
  public String addInvoice(Model model) {
    model.addAttribute("checkouts", checkoutHelper.getAll());
    model.addAttribute("invoices", invoiceHelper.getAll());
    model.addAttribute("pageFragment", "addInvoice");
    return "index";
  }

  @RequestMapping(value = {"/admin/invoice"}, method = RequestMethod.POST)
  public String createInvoice(HttpServletRequest request,
                              HttpServletResponse resp,
                              Model model,
                              @RequestParam Long checkoutId,
                              @RequestParam Double grossPrice,
                              @RequestParam Double taxAmount,
                              @RequestParam String date) {
    LocalDateTime expiryDate = LocalDateTime.parse(date);
    Checkout checkout = checkoutHelper.getById(checkoutId);
    Invoice invoice = invoiceHelper.createInvoice(checkout,grossPrice,taxAmount,expiryDate);
    return "redirect:/admin/invoice";
  }

  @RequestMapping(value = {"/admin/invoice/markIssued"}, method = RequestMethod.POST)
  public String markIssued(Model model,
                           @RequestParam Long id) {
    invoiceHelper.markInvoiceAsIssued(id);
    return "redirect:/admin/invoice";
  }

  @RequestMapping(value = {"/admin/invoice/markPayed"}, method = RequestMethod.POST)
  public String markPayed(Model model,
                          @RequestParam Long id) {
    invoiceHelper.markInvoiceAsPayed(id);
    return "redirect:/admin/invoice";
  }

    @RequestMapping(value = {"/admin/invoice/markExpired"}, method = RequestMethod.POST)
  public String markExpired(Model model,
                            @RequestParam Long id) {
    invoiceHelper.markInvoiceAsExpired(id);
    return "redirect:/admin/invoice";
  }


  @RequestMapping(value = {"/admin/sendMail"}, method = RequestMethod.GET)
  public String sendMail(Model model){
    model.addAttribute("pageFragment", "sendMail");
    return "index";
  }

  @RequestMapping(value = {"/admin/sendMail"}, method = RequestMethod.POST)
  public String sendMailPost(Model model,
                             @RequestParam String email,
                             @RequestParam String subject,
                             @RequestParam String text) {
      try {
          notificationService.sendMessage(email,subject,text);
      } catch (MailException e) {
          return e.getMessage();
      }
      return "index";
  }

}
