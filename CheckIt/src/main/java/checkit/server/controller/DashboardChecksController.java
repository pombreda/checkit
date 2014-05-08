package checkit.server.controller;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.Input;
import checkit.plugin.service.FormStructService;
import checkit.plugin.service.PluginCheckService;
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
    private PluginCheckService pluginService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ReportingService reportingService;
    
    @Autowired
    private FormStructService formStructService;
    
    @Autowired
    MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();
    
    @RequestMapping(value = "/dashboard/checks", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        List<Check> checks = checkService.getCheckList(userId);
        
        model.addAttribute("checks", checks);
        return "/dashboard/checks";
    }

    @RequestMapping(value = "/dashboard/checks/add", method = RequestMethod.GET)
    public String add(ModelMap model, @ModelAttribute Check check) {
        model.addAttribute("plugins", pluginService.getActivePluginList());
        return "/dashboard/checksAdd";
    }

    @RequestMapping(value = "/dashboard/checks/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int checkId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Check check = checkService.getCheckById(checkId);
        if (check.getUserId() == userId) {
            checkService.deleteCheck(checkId);
        }
        return "redirect:/dashboard/checks";
    }

    @RequestMapping(value = "/dashboard/checks/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int checkId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
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
    
    @RequestMapping(value = "/dashboard/checks/connect", method = RequestMethod.GET, params = {"id"})
    public String connect(ModelMap model, @RequestParam(value = "id") int checkId, @ModelAttribute Reporting reporting, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Check check = checkService.getCheckById(checkId);

        if (check.getUserId() == userId) {
            List<Contact> contacts = contactService.getContactList(userId);
            model.addAttribute("contacts", contacts);
            return "/dashboard/checksConnect";
        }
        return "redirect:/dashboard/checks/edit?id=" + checkId;
    }

    @RequestMapping(value = "/dashboard/checks/connect", method = RequestMethod.GET, params = {"contactId", "checkId", "remove"})
    public String connect(ModelMap model, @RequestParam(value = "checkId") int checkId, @RequestParam(value = "contactId") int contactId, @RequestParam(value = "remove") boolean remove, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
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

    @RequestMapping(value = "/dashboard/checks/add", method = RequestMethod.POST)
    public String post(@ModelAttribute Check check, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        check.setUserId(userId);
        if (check.getTitle().equals("")) {
            check.setTitle(messageSource.getMessage("unknownTitle", null, locale));
        }
        checkService.createCheck(check);
        return "redirect:/dashboard/checks";
    }

    @RequestMapping(value = "/dashboard/checks/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Check check, @ModelAttribute FormStruct formStruct, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
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

    @RequestMapping(value = "/dashboard/checks/connect", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute Reporting reporting, Principal principal, @RequestParam(value = "id") int checkId) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
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
