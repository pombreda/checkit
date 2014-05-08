package checkit.server.controller;

import checkit.server.domain.User;
import checkit.server.service.EmailService;
import checkit.server.service.PasswordService;
import checkit.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupUser(@ModelAttribute User user) {
        return "/account/signup";
    }

    @RequestMapping(value = "/registration-complete", method = RequestMethod.GET)
    public String signupUser(ModelMap model) {
        return "/account/signup-complete";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user, ModelMap model) {
        boolean error = false;
        if (user != null) {
            if (userService.getUserByUsername(user.getUsername()) != null) {
                model.addAttribute("usernameConflict", true);
                model.addAttribute("oldUsername", user.getUsername());
                error = true;
            }
            if (user.getUsername().length() < 3) {
                model.addAttribute("usernameLength", true);
                error = true;
            }
            if (!passwordService.isPasswordStrong(user.getPassword())) {
                model.addAttribute("weakPassword", true);
                error = true;
            } else if (!user.getPassword().equals(user.getConfirmPassword())){
                model.addAttribute("confirmPassword", true);
                error = true;
            }
            if (userService.getUserByEmail(user.getEmail()) != null) {
                model.addAttribute("emailConflict", true);
                model.addAttribute("oldEmail", user.getEmail());
                error = true;
            }
            if (!emailService.isEmailValid(user.getEmail())) {
                model.addAttribute("emailValidation", true);
                model.addAttribute("oldEmail", user.getEmail());
                error = true;
            }
            if (!error) {
                userService.createUser(user);
                return "redirect:/registration-complete";
            }
            model.addAttribute("error", true);
            return "/account/signup";
        }
        return "redirect:/registration-complete";
    }
}
