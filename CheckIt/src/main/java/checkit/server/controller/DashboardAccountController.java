/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to user profile.
 * Dashboard section.
 */

package checkit.server.controller;

import checkit.server.domain.User;
import checkit.server.component.EmailComponent;
import checkit.server.component.PasswordComponent;
import checkit.server.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardAccountController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordComponent passwordService;
    
    @Autowired
    private EmailComponent emailService;
    
    /**
     * Controller for displaying /dashboard/account page
     * Controller loads user data and displays form to edit this data.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        model.addAttribute("user", user);
        return "/dashboard/account";
    } 
    
    /**
     * Controller for posting user edit from /dashboard/account page
     * Edit user data
     *
     * @param user Posted user
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.POST)
    public String post(@ModelAttribute User user, ModelMap model, Principal principal) {
        User loggedUser = userService.getLoggedUser(principal);
        if (loggedUser == null) return "redirect:/dashboard/";
        boolean error = false;
        boolean ok = false;
        boolean password = false;
        boolean email = false;
        
        if (!user.getPassword().isEmpty()) {
            if (!passwordService.isPasswordStrong(user.getPassword())) {
                model.addAttribute("weakPassword", true);
                error = true;
            } else if (!user.getPassword().equals(user.getConfirmPassword())) {
                model.addAttribute("confirmError", true);
                error = true;
            } else {
                password = true;
                ok = true;
            }
        }
        if (!loggedUser.getEmail().equals(user.getEmail())) {
            if (userService.getUserByEmail(user.getEmail()) != null) {
                model.addAttribute("emailConflict", true);
                model.addAttribute("oldEmail", user.getEmail());
                error = true;
            } else {
                if (emailService.isEmailValid(user.getEmail())) {
                    email = true;
                    model.addAttribute("emailInfo", true);
                    ok = true;
                } else {
                    model.addAttribute("emailError", true);
                    error = true;
                }
            }
        }
        if (!error && ok) {
            if (password) userService.updateUserPassword(loggedUser, user.getPassword());
            if (email) userService.updateUserEmail(loggedUser, user.getEmail());
            model.addAttribute("ok", true);
        } else if (error) {
            model.addAttribute("error", true);
        }
        return "/dashboard/account";
    } 

    /**
     * Controller for displaying /dashboard/deleteAccount page
     * Delete logged in user
     *
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect
     */
    @RequestMapping(value = "/dashboard/deleteAccount", method = RequestMethod.POST)
    public String delete(Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        userService.deleteUser(user.getUserId());
        return "redirect:/j_spring_security_logout";
    } 
}
