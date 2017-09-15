package mk.ukim.finki.emt.web;

import mk.ukim.finki.emt.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Viktor on 09-Jun-17.
 */
@RestController
public class MailController {


    NotificationServiceImpl notificationService;

    public MailController(NotificationServiceImpl notificationService)
    {
        this.notificationService = notificationService;
    }

    @RequestMapping("/signup")
    public String signup(){
        return "Please sign up";

    }

    @RequestMapping("/signup-success")
    public String signupSuccess(){
        try{
            notificationService.sendMessage("lodi.dodevska@gmail.com","Text","123");
        }catch (MailException e){
            return e.getMessage();
        }
        return "Something";
    }

}
