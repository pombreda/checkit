/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for dashboard index page.
 * Dashboard section.
 */

package checkit.server.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardIndexController {
    
    /**
     * Controller for displaying /dashboard/ page
     * This page is showed after logging in.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/dashboard/", method = RequestMethod.GET)
    public String showDashboard(ModelMap model, Principal principal) {
        String name = principal.getName();
        model.addAttribute("username", name);
        return "/dashboard/index";
    } 
}
