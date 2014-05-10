/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to agents.
 * Admin section.
 */

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

    /**
     * Controller for displaying /admin/agents page
     * Display list of all agents
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/agents", method = RequestMethod.GET)
    public String show(ModelMap model) {
        List<Agent> agentList = agentService.getAgentList();
        model.addAttribute("agents", agentList);
        return "/admin/agents";
    } 

    /**
     * Controller for displaying /admin/agents/add page
     * Page displays form to adding new agent.
     *
     * @param agent Agent class to receive the data
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/agents/add", method = RequestMethod.GET)
    public String add(@ModelAttribute Agent agent) {
        return "/admin/agentsAdd";
    }

    /**
     * Controller for displaying /admin/agents/remove page
     * Controller deletes agent.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param agentId Id of agent to delete
     *
     * @return Address to redirect
     */
    @RequestMapping(value = "/admin/agents/remove", method = RequestMethod.GET, params = {"id"})
    public String remove(ModelMap model, @RequestParam(value = "id") int agentId) {
        agentService.deleteAgent(agentId);
        return "redirect:/admin/agents";
    }

    /**
     * Controller for displaying /admin/agents/edit page
     * Controller loads agent data and displays form to edit this data.
     *
     * @param model Model of page, received from org.springframework.ui.ModelMap
     * @param agentId Id of agent to edit
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/agents/edit", method = RequestMethod.GET, params = {"id"})
    public String detail(ModelMap model, @RequestParam(value = "id") int agentId) {
        Agent agent = agentService.getAgentById(agentId);
        model.addAttribute("agent", agent);
        return "/admin/agentsEdit";
    }

    /**
     * Controller for posting new agent from /admin/agents/add page
     * Add agent
     *
     * @param agent Posted agent
     * @param bindingResult Information about errors from form processing
     *
     * @return Address to redirect or path of HTML tamplate page to display if problem occurs
     */
    @RequestMapping(value = "/admin/agents/add", method = RequestMethod.POST)
    public String post(@Valid Agent agent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/agentsAdd";
        }
        agentService.createAgent(agent);
        return "redirect:/admin/agents";
    }

    /**
     * Controller for posting agent edit from /admin/agents/edit page
     * Edit agent
     *
     * @param agent Posted agent
     * @param bindingResult Information about errors from form processing
     *
     * @return Address to redirect or path of HTML tamplate page to display if problem occurs
     */
    @RequestMapping(value = "/admin/agents/edit", method = RequestMethod.POST)
    public String edit(@Valid Agent agent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/agentsEdit";
        }
        agentService.updateAgent(agent);
        return "redirect:/admin/agents";
    }

}
