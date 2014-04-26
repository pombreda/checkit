/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.controller;

import checkit.plugin.service.PluginCheckService;
import checkit.plugin.service.PluginReportService;
import checkit.server.domain.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminModulesController {
    @Autowired
    private PluginReportService reportService;
    
    @Autowired
    private PluginCheckService checkService;
    
    @RequestMapping(value = "/admin/modules", method = RequestMethod.GET)
    public String show(ModelMap model) {
        model.addAttribute("reports", reportService.getPluginList());
        model.addAttribute("checks", checkService.getPluginList());

        return "/admin/modules";
    } 

    @RequestMapping(value = "/admin/modules/edit", method = RequestMethod.GET, params = {"id", "type"})
    public String show(ModelMap model, @RequestParam(value = "id") String filename, @RequestParam(value = "type") String type) {
        Plugin plugin = null;
        switch (type) {
            case "report":
                plugin = reportService.getPluginByFilename(filename);
                break;
            case "check":
                plugin = checkService.getPluginByFilename(filename);
                break;
        }
        model.addAttribute("plugin", plugin);
        return "/admin/modulesDetail";
    } 

    @RequestMapping(value = "/admin/modules/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Plugin plugin, @RequestParam(value = "type") String type) {
        switch (type) {
            case "report":
                reportService.updatePlugin(plugin);
                break;
            case "check":
                checkService.updatePlugin(plugin);
                break;
        }
        return "redirect:/admin/modules";
    }
}
