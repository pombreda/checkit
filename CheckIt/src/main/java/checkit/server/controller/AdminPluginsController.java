package checkit.server.controller;

import checkit.plugin.service.PluginCheckService;
import checkit.plugin.service.PluginReportService;
import checkit.plugin.domain.Plugin;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPluginsController {
    @Autowired
    private PluginReportService pluginReportService;
    
    @Autowired
    private PluginCheckService pluginCheckService;
    
    @RequestMapping(value = "/admin/plugins", method = RequestMethod.GET)
    public String show(ModelMap model) {
        model.addAttribute("reports", pluginReportService.getPluginList());
        model.addAttribute("checks", pluginCheckService.getPluginList());

        return "/admin/plugins";
    } 

    @RequestMapping(value = "/admin/plugins/edit", method = RequestMethod.GET, params = {"id", "type"})
    public String show(ModelMap model, @RequestParam(value = "id") String filename, @RequestParam(value = "type") String type) {
        Plugin plugin = null;
        switch (type) {
            case "report":
                plugin = pluginReportService.getPluginByFilename(filename);
                break;
            case "check":
                plugin = pluginCheckService.getPluginByFilename(filename);
                break;
        }
        model.addAttribute("plugin", plugin);
        return "/admin/pluginsDetail";
    } 

    @RequestMapping(value = "/admin/plugins/edit", method = RequestMethod.POST)
    public String edit(@Valid Plugin plugin, BindingResult bindingResult, @RequestParam(value = "type") String type) {
        if (bindingResult.hasErrors()) {
            return "/admin/pluginsDetail";
        }
        switch (type) {
            case "report":
                pluginReportService.updatePlugin(plugin);
                break;
            case "check":
                pluginCheckService.updatePlugin(plugin);
                break;
        }
        return "redirect:/admin/plugins";
    }
}
