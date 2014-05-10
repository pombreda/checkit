/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for admin index page.
 * Admin section.
 */

package checkit.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminIndexController {

    /**
     * Controller for displaying /admin/ page
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
    public String show(ModelMap model) {
        return "/admin/index";
    } 
}
