/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to user signing up.
 */

package checkit.server.controller;

import checkit.server.domain.User;
import checkit.server.component.EmailComponent;
import checkit.server.component.PasswordComponent;
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
    private EmailComponent emailService;

    @Autowired
    private PasswordComponent passwordService;

    /**
     * Controller for displaying /signup page
     *
     * @param user User class to receive the data
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupUser(@ModelAttribute User user) {
        return "/account/signup";
    }

    /**
     * Controller for displaying /registration-complete page
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/registration-complete", method = RequestMethod.GET)
    public String signupUser(ModelMap model) {
        return "/account/signup-complete";
    }

    /**
     * Controller for posting new user from /signup page
     * Check posting data and if problem does not occur, create new user
     *
     * @param user Posted user
     * @param model Model of page, received from org.springframework.ui.ModelMap
     *
     * @return Address to redirect or path of HTML tamplate page to display if problem occurs
     */
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
