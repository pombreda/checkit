/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to email confirmation.
 */

package checkit.server.controller;

import checkit.server.service.UserService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmController {
    @Autowired
    UserService userService;
    
    @Autowired
    MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();
    
    /**
     * Controller for displaying /confirm page with activation query
     * Activate new user via activation hash and display result
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param hash Hash of activation request
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET, params = {"activation"})
    public String confirm(ModelMap model, @RequestParam(value = "activation") String hash) {
        if (userService.confirmEmail(hash)) {
            model.addAttribute("message", messageSource.getMessage("system.confirm.signup", null, locale));
        } else {
            model.addAttribute("message", messageSource.getMessage("system.confirm.error", null, locale));
        }
        return "/account/confirm";
    }

    /**
     * Controller for displaying /confirm page with change query
     * Confirm email change via changing hash and display result
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param hash Hash of activation request
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET, params = {"change"})
    public String change(ModelMap model, @RequestParam(value = "change") String hash) {
        if (userService.confirmEmail(hash)) {
            model.addAttribute("message", messageSource.getMessage("system.confirm.change", null, locale));
        } else {
            model.addAttribute("message", messageSource.getMessage("system.confirm.error", null, locale));
        }
        return "/account/confirm";
    }
}
