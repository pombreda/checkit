/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to checks.
 * Dashboard section.
 */

package checkit.server.controller;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.Input;
import checkit.plugin.service.FormStructService;
import checkit.plugin.component.PluginCheckComponent;
import checkit.server.domain.Contact;
import checkit.server.domain.Reporting;
import checkit.server.domain.Check;
import checkit.server.domain.User;
import checkit.server.service.ContactService;
import checkit.server.service.ReportingService;
import checkit.server.service.CheckService;
import checkit.server.service.CheckingService;
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
public class DashboardChecksController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private CheckService checkService;
    
    @Autowired
    private CheckingService checkingService;
    
    @Autowired
    private PluginCheckComponent pluginService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ReportingService reportingService;
    
    @Autowired
    private FormStructService formStructService;
    
    @Autowired
    MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();
    
    /**
     * Controller for displaying /dashboard/checks page
     * Page displays list of all checks.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/checks", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        List<Check> checks = checkService.getCheckList(userId);
        
        model.addAttribute("checks", checks);
        return "/dashboard/checks";
    }

    /**
     * Controller for displaying /dashboard/checks/add page
     * Page displays form to adding new check.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param check Check class to receive the data
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/dashboard/checks/add", method = RequestMethod.GET)
    public String add(ModelMap model, @ModelAttribute Check check) {
        model.addAttribute("plugins", pluginService.getActivePluginList());
        return "/dashboard/checksAdd";
    }

    /**
     * Controller for displaying /dashboard/checks/remove page
     * Controller verifies user and deletes check, if everything is ok.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param checkId Id of check to delete
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/checks/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int checkId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Check check = checkService.getCheckById(checkId);
        if (check.getUserId() == userId) {
            checkService.deleteCheck(checkId);
        }
        return "redirect:/dashboard/checks";
    }

    /**
     * Controller for displaying /dashboard/checks/edit page
     * Controller loads check and displays form to edit this data.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param checkId Id of check to edit
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or address to redirect if problem occurs
     */
    @RequestMapping(value = "/dashboard/checks/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int checkId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Check check = checkService.getCheckById(checkId);
        List<Reporting> reportingList = reportingService.getReportingListByCheck(checkId);
        List<Contact> connectedContacts = contactService.getContactListByReporting(reportingList);
        
        if (check.getUserId() == userId) {
            String pluginFilename = check.getFilename();
            Object plugin = pluginService.getPluginInstance(pluginFilename);
            //get required inputs of plugin
            List<Input> inputs = pluginService.getInputs(plugin);
            //get user values of inputs + set IDs
            FormStruct formStruct = pluginService.getInputValues(inputs, check.getData());
            //need to parse users input with variable number of lines
            model.addAttribute("formStruct", formStruct);
            model.addAttribute("inputs", inputs);
            model.addAttribute("check", check);
            model.addAttribute("contacts", connectedContacts);
            return "/dashboard/checksEdit";
        }
        return "redirect:/dashboard/checks";
    }
    
    /**
     * Controller for displaying /dashboard/checks/connect page with id query
     * Page displays form for adding new reporting to the check
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param checkId Id of check to add reporting
     * @param reporting Reporting class to receive the data
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or address to redirect if problem occurs
     */
    @RequestMapping(value = "/dashboard/checks/connect", method = RequestMethod.GET, params = {"id"})
    public String connect(ModelMap model, @RequestParam(value = "id") int checkId, @ModelAttribute Reporting reporting, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Check check = checkService.getCheckById(checkId);

        if (check.getUserId() == userId) {
            List<Contact> contacts = contactService.getContactList(userId);
            model.addAttribute("contacts", contacts);
            return "/dashboard/checksConnect";
        }
        return "redirect:/dashboard/checks/edit?id=" + checkId;
    }

    /**
     * Controller for displaying /dashboard/checks/connect page with contactId, checkId and remove query
     * Controller verifies user and deletes reporting connected to the check, if everything is ok.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param checkId Id of check to add reporting
     * @param contactId Reporting class to receive the data
     * @param remove Reporting class to receive the data
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/checks/connect", method = RequestMethod.GET, params = {"contactId", "checkId", "remove"})
    public String connect(ModelMap model, @RequestParam(value = "checkId") int checkId, @RequestParam(value = "contactId") int contactId, @RequestParam(value = "remove") boolean remove, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();

        if (remove) {
            Reporting reporting = new Reporting();
            reporting.setContactId(contactId);
            reporting.setCheckId(checkId);
            reporting.setUserId(userId);
            reportingService.deleteReporting(reporting);
        }
        return "redirect:/dashboard/checks/edit?id=" + checkId;
    }

    /**
     * Controller for posting new check from /dashboard/checks/add page
     * Create new check
     *
     * @param check Posted check
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/checks/add", method = RequestMethod.POST)
    public String post(@ModelAttribute Check check, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        check.setUserId(userId);
        if (check.getTitle().equals("")) {
            check.setTitle(messageSource.getMessage("unknownTitle", null, locale));
        }
        checkService.createCheck(check);
        return "redirect:/dashboard/checks";
    }

    /**
     * Controller for posting check edit from /dashboard/checks/edit page
     * Edit check
     *
     * @param check Posted check
     * @param formStruct All data from plugin custom settings to stringify into JSON
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/checks/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Check check, @ModelAttribute FormStruct formStruct, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        if (check.getUserId() == userId) {
            formStruct = formStructService.resolveNull(formStruct);
            String dataJSON = formStructService.getDataJSON(formStruct);
            check.setData(dataJSON);
            checkService.updateCheck(check);
        }
        //run, edit or stop checking
        if (check.isEnabled()) {
            if (checkingService.isAlreadyChecked(check.getCheckId())) {
                checkingService.updateChecking(check);
            } else {
                checkingService.createChecking(check);
            }
        } else if (!check.isEnabled()) {
            checkingService.deleteChecking(check);
        }
        return "redirect:/dashboard/checks";
    }

    /**
     * Controller for posting new reporting from /dashboard/checks/connect page
     * Create new reporting
     *
     * @param reporting Posted reporting
     * @param checkId Id of check to add reporting
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/checks/connect", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute Reporting reporting, @RequestParam(value = "id") int checkId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        int userIdByCheckId = checkService.getCheckById(checkId).getUserId();
        int userIdByContactId = contactService.getContactById(reporting.getContactId()).getUserId();
        if (userIdByContactId == userId && userIdByCheckId == userId) {
            reporting.setCheckId(checkId);
            reporting.setUserId(userId);
            reportingService.createReporting(reporting);
        }
        return "redirect:/dashboard/checks/edit?id=" + checkId;
    }
}
