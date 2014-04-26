/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.server.domain.Contact;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Report;
import checkit.server.domain.Test;
import checkit.server.domain.User;
import checkit.server.service.ContactDetailService;
import checkit.server.service.ContactService;
import checkit.server.service.ReportService;
import checkit.server.service.TestService;
import checkit.server.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ReportService reportService;
    
    @Autowired
    private TestService testService;
    
    @RequestMapping(value = "/dashboard/contacts", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        List<Contact> contacts = contactService.getContactList(userId);
        
        model.addAttribute("contacts", contacts);
        return "/dashboard/contacts";
    } 
    
    @RequestMapping(value = "/dashboard/contacts/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Contact contact) {
        return "/dashboard/contactsAdd";
    }

    @RequestMapping(value = "/dashboard/contacts/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int contactId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Contact contact = contactService.getContactById(contactId);
        if (contact.getUserId() == userId) {
            contactService.deleteContact(contactId);
        }
        return "redirect:/dashboard/contacts";
    }

    @RequestMapping(value = "/dashboard/contacts/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int contactId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Contact contact = contactService.getContactById(contactId);
        List<ContactDetail> contactDetail = contactDetailService.getContactDetailList(contactId, userId);
        contact.setContactDetail(contactDetail);
        List<Report> reports = reportService.getReportListByContact(contactId);
        List<Test> connectedTests = testService.getTestListByReport(reports);
        
        if (contact.getUserId() == userId) {
            model.addAttribute("contact", contact);
            model.addAttribute("tests", connectedTests);
            return "/dashboard/contactsEdit";
        }
        return "redirect:/dashboard/contacts";
    }

    @RequestMapping(value = "/dashboard/contacts/connect", method = RequestMethod.GET, params = {"id"})
    public String connect(ModelMap model, @RequestParam(value = "id") int contactId, @ModelAttribute Report report, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Contact contact = contactService.getContactById(contactId);

        if (contact.getUserId() == userId) {
            List<Test> tests = testService.getTestList(userId);
            model.addAttribute("tests", tests);
            return "/dashboard/contactsConnect";
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactId;
    }

    @RequestMapping(value = "/dashboard/contacts/connect", method = RequestMethod.GET, params = {"contactId", "testId", "remove"})
    public String connect(ModelMap model, @RequestParam(value = "testId") int testId, @RequestParam(value = "contactId") int contactId, @RequestParam(value = "remove") boolean remove, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();

        if (remove) {
            Report report = new Report();
            report.setContactId(contactId);
            report.setTestId(testId);
            report.setUserId(userId);
            reportService.deleteReport(report);
        }
        return "redirect:/dashboard/contacts/edit?id=" + testId;
    }

    @RequestMapping(value = "/dashboard/contacts/add", method = RequestMethod.POST)
    public String post(@ModelAttribute Contact contact, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        contact.setUserId(userId);
        if (contact.getTitle().equals("")) {
            contact.setTitle("Unknown title");
        }
        contactService.createContact(contact);
        return "redirect:/dashboard/contacts";
    }

    @RequestMapping(value = "/dashboard/contacts/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Contact contact, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        if (contact.getUserId() != userId) {
            return "redirect:/dashboard/contacts";
        }
        contactService.updateContact(contact);
        return "redirect:/dashboard/contacts";
    }

    @RequestMapping(value = "/dashboard/contacts/connect", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute Report report, Principal principal, @RequestParam(value = "id") int contactId) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        int userIdByContactId = contactService.getContactById(contactId).getUserId();
        int userIdByTestId = testService.getTestById(report.getTestId()).getUserId();
        if (userIdByContactId == userId && userIdByTestId == userId) {
            report.setContactId(contactId);
            report.setUserId(userId);
            reportService.createReport(report);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactId;
    }
}
