/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dashboard/reports")
public class DashboardReportsController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        return "/dashboard/reports";
    } 
}
