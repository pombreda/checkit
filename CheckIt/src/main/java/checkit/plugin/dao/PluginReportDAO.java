/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.dao;

import checkit.server.domain.Plugin;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface PluginReportDAO {
    public List<Plugin> getPluginReportList();
    public List<Plugin> getActivePluginReportList();
    public void createPluginReport(Plugin plugin);
    public void deletePluginReport(String filename);
    public void updatePluginReport(Plugin plugin);
    public Plugin getPluginReportByFilename(String filename);

}
