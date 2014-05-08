package checkit.server.controller;

import checkit.plugin.service.PluginCheckService;
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
    private PluginCheckService pluginService;
    
    @RequestMapping(value = "/dashboard/results", method = RequestMethod.GET)
    public String show(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        int userId = user.getUserId();
        List<Check> checkList = checkService.getCheckList(userId);
        model.addAttribute("checks", checkList);
        return "/dashboard/results";
    } 
    
    @RequestMapping(value = "/dashboard/results/detail", method = RequestMethod.GET, params = {"id"})
    public String show(ModelMap model, @RequestParam(value = "id") int checkId, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
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
