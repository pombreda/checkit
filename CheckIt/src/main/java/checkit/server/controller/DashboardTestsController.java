/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.service.FormStructService;
import checkit.plugin.service.PluginCheckService;
import checkit.server.domain.Contact;
import checkit.server.domain.Report;
import checkit.server.domain.Test;
import checkit.server.domain.User;
import checkit.server.service.ContactService;
import checkit.server.service.ReportService;
import checkit.server.service.TestService;
import checkit.server.service.TestingService;
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
public class DashboardTestsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private TestingService testingService;
    
    @Autowired
    private PluginCheckService pluginService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private FormStructService formStructService;
    
    @RequestMapping(value = "/dashboard/tests", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        List<Test> tests = testService.getTestList(userId);
        
        model.addAttribute("tests", tests);
        return "/dashboard/tests";
    }

    @RequestMapping(value = "/dashboard/tests/add", method = RequestMethod.GET)
    public String add(ModelMap model, @ModelAttribute Test test) {
        model.addAttribute("plugins", pluginService.getActivePluginList());
        return "/dashboard/testsAdd";
    }

    @RequestMapping(value = "/dashboard/tests/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int testId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Test test = testService.getTestById(testId);
        if (test.getUserId() == userId) {
            testService.deleteTest(testId);
        }
        return "redirect:/dashboard/tests";
    }

    @RequestMapping(value = "/dashboard/tests/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int testId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Test test = testService.getTestById(testId);
        List<Report> reports = reportService.getReportListByTest(testId);
        List<Contact> connectedContacts = contactService.getContactListByReport(reports);
        
        if (test.getUserId() == userId) {
            String pluginFilename = test.getPluginFilename();
            Object plugin = pluginService.getPluginInstance(pluginFilename);
            //get required inputs of plugin
            List< List<String> > inputs = pluginService.getInputs(plugin);
            //get user values of inputs + set IDs
            FormStruct formStruct = pluginService.getInputValues(inputs, test.getData());
            //need to parse users input with variable number of lines
            model.addAttribute("formStruct", formStruct);
            model.addAttribute("inputs", inputs);
            model.addAttribute("test", test);
            model.addAttribute("contacts", connectedContacts);
            return "/dashboard/testsEdit";
        }
        return "redirect:/dashboard/tests";
    }
    
    @RequestMapping(value = "/dashboard/tests/connect", method = RequestMethod.GET, params = {"id"})
    public String connect(ModelMap model, @RequestParam(value = "id") int testId, @ModelAttribute Report report, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Test test = testService.getTestById(testId);

        if (test.getUserId() == userId) {
            List<Contact> contacts = contactService.getContactList(userId);
            model.addAttribute("contacts", contacts);
            return "/dashboard/testsConnect";
        }
        return "redirect:/dashboard/tests/edit?id=" + testId;
    }

    @RequestMapping(value = "/dashboard/tests/connect", method = RequestMethod.GET, params = {"contactId", "testId", "remove"})
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
        return "redirect:/dashboard/tests/edit?id=" + testId;
    }

    @RequestMapping(value = "/dashboard/tests/add", method = RequestMethod.POST)
    public String post(@ModelAttribute Test test, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        test.setUserId(userId);
        if (test.getTitle().equals("")) {
            test.setTitle("Unknown title");
        }
        testService.createTest(test);
        return "redirect:/dashboard/tests";
    }

    @RequestMapping(value = "/dashboard/tests/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Test test, @ModelAttribute FormStruct formStruct, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        if (test.getUserId() == userId) {
            formStruct = formStructService.resolveNull(formStruct);
            String dataJSON = formStructService.getDataJSON(formStruct);
            test.setData(dataJSON);
            testService.updateTest(test);
        }
        //run, edit or stop testing
        if (test.isEnabled()) {
            if (testingService.isAlreadyTested(test.getTestId())) {
                testingService.updateTesting(test);
            } else {
                testingService.createTesting(test);
            }
        } else if (!test.isEnabled()) {
            testingService.deleteTesting(test);
        }
        return "redirect:/dashboard/tests";
    }

    @RequestMapping(value = "/dashboard/tests/connect", method = RequestMethod.POST, params = {"id"})
    public String add(@ModelAttribute Report report, Principal principal, @RequestParam(value = "id") int testId) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        int userIdByTestId = testService.getTestById(testId).getUserId();
        int userIdByContactId = contactService.getContactById(report.getContactId()).getUserId();
        if (userIdByContactId == userId && userIdByTestId == userId) {
            report.setTestId(testId);
            report.setUserId(userId);
            reportService.createReport(report);
        }
        return "redirect:/dashboard/tests/edit?id=" + testId;
    }
}
