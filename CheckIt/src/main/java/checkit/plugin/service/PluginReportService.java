/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.service;

import checkit.plugin.dao.PluginReportDAO;
import checkit.server.domain.Plugin;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PluginReportService extends PluginServiceAbstract {
    @Autowired
    private PluginReportDAO pluginReportDAO;

    @Override
    protected String getPath() {
        return "D:\\Skola\\CVUT\\bakule\\! zdrojak\\plugins\\report\\";
    }

    @Override
    protected String getClassPrefix() {
        return "checkit.plugin.report";
    }

    @Override
    public void registerPlugin(String filename) {
        Plugin report = new Plugin();
        report.setFilename(filename);
        report.setEnabled(false);
        report.setTitle(filename);
        report.setDescription(" ");
        
        pluginReportDAO.createPluginReport(report);
    }

    @Override
    public void deletePlugin(String filename) {
        pluginReportDAO.deletePluginReport(filename);
    }

    @Override
    public Plugin getPluginByFilename(String filename) {
        return pluginReportDAO.getPluginReportByFilename(filename);
    }
    
    @Override
    public List<Plugin> getPluginList() {
        return pluginReportDAO.getPluginReportList();
    }

    @Override
    public List<Plugin> getActivePluginList() {
        return pluginReportDAO.getActivePluginReportList();
    }

    @Override
    public void updatePlugin(Plugin plugin) {
        pluginReportDAO.updatePluginReport(plugin);
    }

}
