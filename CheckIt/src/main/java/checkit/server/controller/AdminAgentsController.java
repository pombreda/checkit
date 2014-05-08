package checkit.server.controller;

import checkit.server.domain.Agent;
import checkit.server.service.AgentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminAgentsController {
    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/admin/agents", method = RequestMethod.GET)
    public String show(ModelMap model) {
        List<Agent> agentList = agentService.getAgentList();
        model.addAttribute("agents", agentList);
        return "/admin/agents";
    } 

    @RequestMapping(value = "/admin/agents/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Agent agent) {
        return "/admin/agentsAdd";
    }

    @RequestMapping(value = "/admin/agents/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int agentId) {
        agentService.deleteAgent(agentId);
        return "redirect:/admin/agents";
    }

    @RequestMapping(value = "/admin/agents/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int agentId) {
        Agent agent = agentService.getAgentById(agentId);
        model.addAttribute("agent", agent);
        return "/admin/agentsEdit";
    }

    @RequestMapping(value = "/admin/agents/add", method = RequestMethod.POST)
    public String post(@Valid Agent agent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/agentsAdd";
        }
        agentService.createAgent(agent);
        return "redirect:/admin/agents";
    }

    @RequestMapping(value = "/admin/agents/edit", method = RequestMethod.POST, params = {"id"})
    public String edit(@Valid Agent agent, BindingResult bindingResult, @RequestParam(value = "id") int agentId) {
        if (bindingResult.hasErrors()) {
            return "/admin/agentsEdit";
        }
        agentService.updateAgent(agent);
        return "redirect:/admin/agents";
    }


}
