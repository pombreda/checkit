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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmController {
    @Autowired
    UserService userService;
    
    @RequestMapping(value = "/confirm", method = RequestMethod.GET, params = {"activation"})
    public String confirm(ModelMap model, @RequestParam(value = "activation") String hash) {
        if (userService.confirmEmail(hash)) {
            model.addAttribute("message", "Confirmation was successful. You can now sign in to CheckIT.");
        } else {
            model.addAttribute("message", "Sorry, your link expired or never existed.");
        }
        return "/account/confirm";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET, params = {"change"})
    public String change(ModelMap model, @RequestParam(value = "change") String hash) {
        if (userService.confirmEmail(hash)) {
            model.addAttribute("message", "Confirmation was successful. Your email has been updated.");
        } else {
            model.addAttribute("message", "Sorry, your link expired or never existed.");
        }
        return "/account/confirm";
    }
}
