/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.service;

import checkit.plugin.dao.PluginCheckDAO;
import checkit.server.domain.Plugin;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PluginCheckService extends PluginServiceAbstract {
    @Autowired
    private PluginCheckDAO pluginCheckDAO;

    @Override
    protected String getPath() {
        return "D:\\Skola\\CVUT\\bakule\\! zdrojak\\plugins\\check\\";
    }

    @Override
    protected String getClassPrefix() {
        return "checkit.plugin.check";
    }

    @Override
    public void registerPlugin(String filename) {
        Plugin report = new Plugin();
        report.setFilename(filename);
        report.setEnabled(false);
        report.setTitle(filename);
        report.setDescription(" ");
        
        pluginCheckDAO.createPluginCheck(report);
    }

    @Override
    public void deletePlugin(String filename) {
        pluginCheckDAO.deletePluginCheck(filename);
    }

    @Override
    public Plugin getPluginByFilename(String filename) {
        return pluginCheckDAO.getPluginCheckByFilename(filename);
    }
    
    @Override
    public List<Plugin> getPluginList() {
        return pluginCheckDAO.getPluginCheckList();
    }

    @Override
    public List<Plugin> getActivePluginList() {
        return pluginCheckDAO.getActivePluginCheckList();
    }

    @Override
    public void updatePlugin(Plugin plugin) {
        pluginCheckDAO.updatePluginCheck(plugin);
    }

}
