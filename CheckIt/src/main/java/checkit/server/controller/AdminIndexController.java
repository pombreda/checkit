package checkit.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/")
public class AdminIndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model) {
        return "/admin/index";
    } 
}
