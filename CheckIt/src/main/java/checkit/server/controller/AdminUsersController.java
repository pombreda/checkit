/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to administration of users.
 * Admin section.
 */

package checkit.server.controller;

import checkit.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminUsersController {
    @Autowired
    UserService userService;
    
    /**
     * Controller for displaying /admin/users page
     * Display list of all users
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String show(ModelMap model) {
        model.addAttribute("users", userService.getUserList());
        return "/admin/users";
    } 
}
