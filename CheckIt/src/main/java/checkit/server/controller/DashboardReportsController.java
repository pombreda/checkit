/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.server.domain.Result;
import checkit.server.domain.Test;
import checkit.server.domain.User;
import checkit.server.service.ResultService;
import checkit.server.service.TestService;
import checkit.server.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardReportsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private ResultService resultService;
    
    @RequestMapping(value = "/dashboard/reports", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        List<Test> testList = testService.getTestList(userId);
        model.addAttribute("tests", testList);
        return "/dashboard/reports";
    } 
    
    @RequestMapping(value = "/dashboard/reports/detail", method = RequestMethod.GET, params = {"id"})
    public String show(ModelMap model, @RequestParam(value = "id") int testId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        Test test = testService.getTestById(testId);
        if (test.getUserId() == userId) {
            List<Result> results = resultService.getResultList(testId);
            model.addAttribute("title", test.getTitle());
            model.addAttribute("results", results);
            return "/dashboard/reportsDetail";
        }
        return "redirect:/dashboard/reports";
    } 
}
