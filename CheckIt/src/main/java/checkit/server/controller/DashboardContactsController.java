/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to contacts.
 * Dashboard section.
 */

package checkit.server.controller;

import checkit.server.domain.Contact;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Reporting;
import checkit.server.domain.Check;
import checkit.server.domain.User;
import checkit.server.service.ContactDetailService;
import checkit.server.service.ContactService;
import checkit.server.service.ReportingService;
import checkit.server.service.CheckService;
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
public class DashboardContactsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
    @Autowired
    private ReportingService reportingService;
    
    @Autowired
    private CheckService checkService;
    
    @Autowired
    MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();
    
    /**
     * Controller for displaying /dashboard/contacts page
     * Page displays list of all contacts.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contacts", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        List<Contact> contacts = contactService.getContactList(userId);
        
        model.addAttribute("contacts", contacts);
        return "/dashboard/contacts";
    } 
    
    /**
     * Controller for displaying /dashboard/contacts/add page
     * Page displays form to adding new contact.
     *
     * @param contact Contact class to receive the data
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/dashboard/contacts/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Contact contact) {
        return "/dashboard/contactsAdd";
    }

    /**
     * Controller for displaying /dashboard/contacts/remove page
     * Controller verifies user and deletes contact, if everything is ok.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param contactId Id of contact to delete
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contacts/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int contactId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Contact contact = contactService.getContactById(contactId);
        if (contact.getUserId() == userId) {
            contactService.deleteContact(contactId);
        }
        return "redirect:/dashboard/contacts";
    }

    /**
     * Controller for displaying /dashboard/contacts/edit page
     * Controller loads contact data and displays form to edit this data.
     * It displays all contact detail of corresponding contact and all reporting belongs to it as well.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param contactId Id of contact to edit
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or address to redirect if problem occurs
     */
    @RequestMapping(value = "/dashboard/contacts/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int contactId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Contact contact = contactService.getContactById(contactId);
        List<ContactDetail> contactDetail = contactDetailService.getContactDetailList(contactId, userId);
        contact.setContactDetail(contactDetail);
        List<Reporting> reportingList = reportingService.getReportingListByContact(contactId);
        List<Check> connectedChecks = checkService.getCheckListByReporting(reportingList);
        
        if (contact.getUserId() == userId) {
            model.addAttribute("contact", contact);
            model.addAttribute("checks", connectedChecks);
            return "/dashboard/contactsEdit";
        }
        return "redirect:/dashboard/contacts";
    }

    /**
     * Controller for displaying /dashboard/contacts/connect page with id query
     * Page displays form for adding new reporting to the contact
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param contactId Id of contact to add reporting
     * @param reporting Reporting class to receive the data
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or address to redirect if problem occurs
     */
    @RequestMapping(value = "/dashboard/contacts/connect", method = RequestMethod.GET, params = {"id"})
    public String connect(ModelMap model, @RequestParam(value = "id") int contactId, @ModelAttribute Reporting reporting, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Contact contact = contactService.getContactById(contactId);

        if (contact.getUserId() == userId) {
            List<Check> checks = checkService.getCheckList(userId);
            model.addAttribute("checks", checks);
            return "/dashboard/contactsConnect";
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactId;
    }

    /**
     * Controller for displaying /dashboard/contacts/connect page with checkId, contactId and remove query
     * Controller verifies user and deletes reporting connected to the contact, if everything is ok.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param checkId Id of check to add reporting
     * @param contactId Reporting class to receive the data
     * @param remove Reporting class to receive the data
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contacts/connect", method = RequestMethod.GET, params = {"contactId", "checkId", "remove"})
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
        return "redirect:/dashboard/contacts/edit?id=" + contactId;
    }

    /**
     * Controller for posting new contact from /dashboard/contacts/add page
     * Create new contact
     *
     * @param contact Posted contact
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contacts/add", method = RequestMethod.POST)
    public String post(@ModelAttribute Contact contact, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        contact.setUserId(userId);
        if (contact.getTitle().equals("")) {
            contact.setTitle(messageSource.getMessage("unknownTitle", null, locale));
        }
        contactService.createContact(contact);
        return "redirect:/dashboard/contacts";
    }

    /**
     * Controller for posting check edit from /dashboard/contacts/edit page
     * Edit contact
     *
     * @param contact Posted contact
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contacts/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Contact contact, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        if (contact.getUserId() == userId) {
            contactService.updateContact(contact);
        }
        return "redirect:/dashboard/contacts";
    }

    /**
     * Controller for posting new reporting from /dashboard/contacts/connect page
     * Create new reporting
     *
     * @param reporting Posted reporting
     * @param contactId Id of contact to add reporting
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Address to redirect or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/contacts/connect", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute Reporting reporting, Principal principal, @RequestParam(value = "id") int contactId) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        int userIdByContactId = contactService.getContactById(contactId).getUserId();
        int userIdByCheckId = checkService.getCheckById(reporting.getCheckId()).getUserId();
        if (userIdByContactId == userId && userIdByCheckId == userId) {
            reporting.setContactId(contactId);
            reporting.setUserId(userId);
            reportingService.createReporting(reporting);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactId;
    }
}
