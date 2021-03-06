/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for features page.
 */

package checkit.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FeaturesController {

    /**
     * Controller for displaying /features page
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/features", method = RequestMethod.GET)
    public String showIndex(ModelMap model) {
        return "/presentation/features";
    } 

}
