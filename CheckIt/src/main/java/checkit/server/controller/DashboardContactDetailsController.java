/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.Input;
import checkit.plugin.service.FormStructService;
import checkit.plugin.service.PluginReportService;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.User;
import checkit.server.service.ContactDetailService;
import checkit.server.service.ContactService;
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

/**
 *
 * @author Dodo
 */
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
    private PluginReportService reportService;
    
    @RequestMapping(value = "/dashboard/contactDetail/add", method = RequestMethod.GET, params = {"id"})
    public String add(ModelMap model, @ModelAttribute ContactDetail contactDetail) {
        model.addAttribute("plugins", reportService.getActivePluginList());
        return "/dashboard/contactDetailAdd";
    }

    @RequestMapping(value = "/dashboard/contactDetail/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int contactDetailId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        ContactDetail contactDetail = contactDetailService.getContactDetailById(contactDetailId);
        if (contactDetail.getUserId() == userId) {
            contactDetailService.deleteContactDetail(contactDetailId);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }

    @RequestMapping(value = "/dashboard/contactDetail/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int contactDetailId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        ContactDetail contactDetail = contactDetailService.getContactDetailById(contactDetailId);

        if (contactDetail.getUserId() == userId) {
            String pluginFilename = contactDetail.getPluginFilename();
            Object plugin = reportService.getPluginInstance(pluginFilename);
            //get required inputs of plugin
            List<Input> inputs = reportService.getInputs(plugin);
            //get user values of inputs + set IDs
            FormStruct formStruct = reportService.getInputValues(inputs, contactDetail.getData());

            model.addAttribute("contactDetail", contactDetail);
            //need to parse users input with variable number of lines
            model.addAttribute("formStruct", formStruct);
            model.addAttribute("inputs", inputs);
            return "/dashboard/contactDetailEdit";
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }

    @RequestMapping(value = "/dashboard/contactDetail/add", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute ContactDetail contactDetail, Principal principal, @RequestParam(value = "id") int contactId) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();

        if (contactDetail.getTitle().equals("")) {
            contactDetail.setTitle("Unknown title");
        }
        int userIdByContactId = contactService.getContactById(contactId).getUserId();
        if (userIdByContactId == userId) {
            contactDetail.setContactId(contactId);
            contactDetail.setUserId(userId);
            contactDetailService.createContactDetail(contactDetail);
        }
        return "redirect:/dashboard/contacts/edit?id=" + contactDetail.getContactId();
    }

    @RequestMapping(value = "/dashboard/contactDetail/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute ContactDetail contactDetail, @ModelAttribute FormStruct formStruct, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
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
