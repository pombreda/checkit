/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.server.domain.Agent;
import checkit.server.service.AgentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        List<Agent> agents = agentService.getAgentList();
        model.addAttribute("agents", agents);
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
    public String post(@ModelAttribute Agent agent) {
        //TODO - address validation (URL, IP, subfolders....)
        if (agent.getIp().equals("")) {
            return "redirect:/admin/agents/add";
        }
        if (agent.getLocation().equals("")) {
            agent.setLocation("Unknown location");
        }
        agentService.createAgent(agent);
        return "redirect:/admin/agents";
    }

    @RequestMapping(value = "/admin/agents/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Agent agent) {
        agentService.updateAgent(agent);
        return "redirect:/admin/agents";
    }


}
