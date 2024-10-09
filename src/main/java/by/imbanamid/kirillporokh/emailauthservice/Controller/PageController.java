package by.imbanamid.kirillporokh.emailauthservice.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

  @GetMapping("/login")
  public String loginPage() {
    return "login"; // login.html
  }

  @GetMapping("/user")
  public String userPage(Authentication authentication, Model model) {
    String email = authentication.getName();
    model.addAttribute("email", email);
    return "user"; // user.html
  }
}
