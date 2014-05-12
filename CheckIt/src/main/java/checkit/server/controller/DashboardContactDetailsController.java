/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to contact details.
 * Dashboard section.
 */

package checkit.server.controller;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.Input;
import checkit.plugin.service.FormStructService;
import checkit.plugin.component.PluginReportComponent;
import checkit.plugin.service.PluginReportService;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.User;
import checkit.server.service.ContactDetailService;
import checkit.server.service.ContactService;
import checkit.server.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardContactDetailsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
    @Autowired
    private FormStructService formStructService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private PluginReportComponent reportComponent;
    
    @Autowired
    private PluginReportService reportService;
    
    @Autowired
    MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();
    
    /**
     * Controller for displaying /dashboard/contactDetail/add page with parent contact id query
     * Page displays form to adding new contact detail into the contact.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param contactDetail Contact detail class to receive the data
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/dashboard/contactDetail/add", method = RequestMethod.GET, params = {"id"})
    public String add(ModelMap model, @ModelAttribute ContactDetail contactDetail) {
        model.addAttribute("plugins", reportService.getActivePluginList());
        return "/dashboard/contactDetailAdd";
    }

    /**
     * Controller for displaying /dashboard/contactDetail/remove page
     * Controller verifies user and deletes contact detail, if everything is ok.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param contactDetailId Id of contact detail to delete
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contactDetail/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int contactDetailId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        ContactDetail contactDetail = contactDetailService.getContactDetailById(contactDetailId);
        if (contactDetail.getUserId() == userId) {
            contactDetailService.deleteContactDetail(contactDetailId);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }

    /**
     * Controller for displaying /dashboard/contactDetail/edit page
     * Controller loads contact detail data and displays form to edit this data.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param contactDetailId Id of contact detail to edit
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or address to redirect if problem occurs
     */
    @RequestMapping(value = "/dashboard/contactDetail/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int contactDetailId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        ContactDetail contactDetail = contactDetailService.getContactDetailById(contactDetailId);

        if (contactDetail.getUserId() == userId) {
            String pluginFilename = contactDetail.getFilename();
            Object plugin = reportComponent.getPluginInstance(pluginFilename);
            //get required inputs of plugin
            List<Input> inputs = reportComponent.getInputs(plugin);
            //get user values of inputs + set IDs
            FormStruct formStruct = reportComponent.getInputValues(inputs, contactDetail.getData());

            model.addAttribute("contactDetail", contactDetail);
            //need to parse users input with variable number of lines
            model.addAttribute("formStruct", formStruct);
            model.addAttribute("inputs", inputs);
            return "/dashboard/contactDetailEdit";
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }

    /**
     * Controller for posting new contact detail from /dashboard/contactDetail/add page
     * Create new contact detail
     *
     * @param contactDetail Posted contact detail
     * @param contactId Id of parent contact for adding contact detail into
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contactDetail/add", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute ContactDetail contactDetail, @RequestParam(value = "id") int contactId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();

        if (contactDetail.getTitle().equals("")) {
            contactDetail.setTitle(messageSource.getMessage("unknownTitle", null, locale));
        }
        int userIdByContactId = contactService.getContactById(contactId).getUserId();
        if (userIdByContactId == userId) {
            contactDetail.setContactId(contactId);
            contactDetail.setUserId(userId);
            contactDetailService.createContactDetail(contactDetail);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }

    /**
     * Controller for posting contact detail edit from /dashboard/contactDetail/edit page
     * Edit contact detail
     *
     * @param contactDetail Posted contact detail
     * @param formStruct All data from plugin custom settings to stringify into JSON
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contactDetail/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute ContactDetail contactDetail, @ModelAttribute FormStruct formStruct, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        if (contactDetail.getUserId() == userId) {
            formStruct = formStructService.resolveNull(formStruct);
            String dataJSON = formStructService.getDataJSON(formStruct);
            contactDetail.setData(dataJSON);
            contactDetailService.updateContactDetail(contactDetail);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }
}
