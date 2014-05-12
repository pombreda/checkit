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

import checkit.plugin.domain.Plugin;
import checkit.plugin.domain.PluginCheck;
import checkit.plugin.domain.PluginReport;
import checkit.plugin.service.PluginCheckService;
import checkit.plugin.service.PluginReportService;
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
     * Controller for displaying /admin/plugins/editReport page
     * Controller loads plugin data and displays form to edit this data.
     *
     * @param model Model of page
     * @param filename Filename of plugin to edit
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/plugins/editReport", method = RequestMethod.GET, params = {"id"})
    public String showReport(ModelMap model, @RequestParam(value = "id") String filename) {
        PluginReport plugin = null;
        plugin = pluginReportService.getPluginByFilename(filename);
        model.addAttribute("plugin", plugin);
        return "/admin/pluginsReportDetail";
    } 

    /**
     * Controller for displaying /admin/plugins/editReport page
     * Controller loads plugin data and displays form to edit this data.
     *
     * @param model Model of page
     * @param filename Filename of plugin to edit
     *
     * @return Path of HTML tamplate page to display
     */
    @RequestMapping(value = "/admin/plugins/editCheck", method = RequestMethod.GET, params = {"id"})
    public String showCheck(ModelMap model, @RequestParam(value = "id") String filename) {
        PluginCheck plugin = null;
        plugin = pluginCheckService.getPluginByFilename(filename);
        model.addAttribute("plugin", plugin);
        return "/admin/pluginsCheckDetail";
    } 

    /**
     * Controller for posting report plugin edit from /admin/plugins/editReport page
     * Edit report plugin
     *
     * @param plugin Posted plugin
     * @param bindingResult Information about errors from form processing
     *
     * @return Address to redirect or path of HTML tamplate page to display if problem occurs
     */
    @RequestMapping(value = "/admin/plugins/editReport", method = RequestMethod.POST)
    public String editReport(@Valid PluginReport plugin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/pluginsReportDetail";
        }
        pluginReportService.updatePlugin(plugin);
        return "redirect:/admin/plugins";
    }
    
    /**
     * Controller for posting check plugin edit from /admin/plugins/editCheck page
     * Edit check plugin
     *
     * @param plugin Posted plugin
     * @param bindingResult Information about errors from form processing
     *
     * @return Address to redirect or path of HTML tamplate page to display if problem occurs
     */
    @RequestMapping(value = "/admin/plugins/editCheck", method = RequestMethod.POST)
    public String editCheck(@Valid PluginCheck plugin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/pluginsCheckDetail";
        }
        pluginCheckService.updatePlugin(plugin);
        return "redirect:/admin/plugins";
    }
}
