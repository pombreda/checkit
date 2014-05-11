/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Controller for everything related to plugins.
 * Admin section.
 */

package checkit.server.controller;

import checkit.plugin.component.PluginCheckComponent;
import checkit.plugin.component.PluginReportComponent;
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
    private PluginReportComponent pluginReportService;
    
    @Autowired
    private PluginCheckComponent pluginCheckService;
    
    /**
     * Controller for displaying /admin/plugins page
     * Display list of all plugins
     *
     * @param model Model of page
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/plugins", method = RequestMethod.GET)
    public String show(ModelMap model) {
        model.addAttribute("reports", pluginReportService.getPluginList());
        model.addAttribute("checks", pluginCheckService.getPluginList());

        return "/admin/plugins";
    } 

    /**
     * Controller for displaying /admin/plugins/edit page
     * Controller loads plugin data and displays form to edit this data.
     *
     * @param model Model of page
     * @param filename Filename of plugin to edit
     * @param type Type of plugin to edit (e.g. report, check)
     *
     * @return Path of HTML tamplate page to display
     */
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

    /**
     * Controller for posting plugin edit from /admin/plugins/edit page
     * Edit plugin
     *
     * @param plugin Posted agent
     * @param bindingResult Information about errors from form processing
     * @param type Type of edited plugin (e.g. report, check)
     *
     * @return Address to redirect or path of HTML tamplate page to display if problem occurs
     */
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
