/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
    @Autowired
    UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model) {
        model.addAttribute("users", userService.getUserList());
        return "/admin/users";
    } 
}
