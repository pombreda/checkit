package checkit.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value="/signin", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "/account/login";
    }

    @RequestMapping(value="/sorry", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "/account/login";
    }

    @RequestMapping(value="/signout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "/account/login";
    }    
}
