/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.server.domain.User;
import checkit.server.service.EmailService;
import checkit.server.service.PasswordService;
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
    private PasswordService passwordService;
    
    @Autowired
    private EmailService emailService;
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "/dashboard/account";
    } 
    
    @RequestMapping(value = "/dashboard/account", method = RequestMethod.POST)
    public String post(@ModelAttribute User user, ModelMap model, Principal principal) {
        String loggedUsername = principal.getName();
        User loggedUser = userService.getUserByUsername(loggedUsername);
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

    @RequestMapping(value = "/dashboard/deleteAccount", method = RequestMethod.POST)
    public String delete(Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        userService.deleteUser(user.getUserId());
        return "redirect:/j_spring_security_logout";
    } 
}
