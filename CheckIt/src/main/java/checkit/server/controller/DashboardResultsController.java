/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to results.
 * Dashboard section.
 */

package checkit.server.controller;

import checkit.plugin.component.PluginCheckComponent;
import checkit.server.domain.Result;
import checkit.server.domain.Check;
import checkit.server.domain.User;
import checkit.server.service.ResultService;
import checkit.server.service.CheckService;
import checkit.server.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardResultsController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private CheckService checkService;
    
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private PluginCheckComponent pluginService;
    
    /**
     * Controller for displaying /dashboard/results page
     * Page displays list of all checks.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or redirect to login form if user is not logged in
     */
    @RequestMapping(value = "/dashboard/results", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        List<Check> checkList = checkService.getCheckList(userId);
        model.addAttribute("checks", checkList);
        return "/dashboard/results";
    } 
    
    /**
     * Controller for displaying /dashboard/results/detail page
     * Page displays detail results of corresponding check.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param checkId Id of check to display its results
     * @param principal Information about logged in user, received from java.security.Principal
     *
     * @return Path of HTML tamplate page to display or address to redirect if problem occurs
     */
    @RequestMapping(value = "/dashboard/results/detail", method = RequestMethod.GET, params = {"id"})
    public String show(ModelMap model, @RequestParam(value = "id") int checkId, Principal principal) {
        User user = userService.getLoggedUser(principal);
        if (user == null) return "redirect:/dashboard/";
        int userId = user.getUserId();
        Check check = checkService.getCheckById(checkId);
        if (check.getUserId() == userId) {
            List<Result> results = resultService.getResultList(checkId);
            List<Result> graphResults = resultService.createResultGraphOutput(resultService.getResultListAsc(checkId));
            
            List<String> data = new ArrayList<String>();
            for (Result result : results) {
                data.add(result.getData());
            }
            
            List<String> tableHeader = pluginService.getTableHeader(check.getFilename());
            List< List<Object> > tableValues = pluginService.getTableValues(check.getFilename(), data);

            model.addAttribute("title", check.getTitle());
            model.addAttribute("results", results);
            model.addAttribute("chartLastWeek", resultService.getTimesForLastDays(graphResults, 7));
            model.addAttribute("chartLastMonth", resultService.getTimesForLastDays(graphResults, 30));
            model.addAttribute("chartAllTime", resultService.getTimesForLastDays(graphResults, -1));
            model.addAttribute("chartLastDay", resultService.getDataForLastDays(graphResults, 1));
            model.addAttribute("tableHeader", tableHeader);
            model.addAttribute("tableValues", tableValues);
            return "/dashboard/resultsDetail";
        }
        return "redirect:/dashboard/results";
    } 
}
