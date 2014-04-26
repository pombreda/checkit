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
public interface PluginCheckDAO {
    public List<Plugin> getPluginCheckList();
    public List<Plugin> getActivePluginCheckList();
    public void createPluginCheck(Plugin plugin);
    public void deletePluginCheck(String filename);
    public void updatePluginCheck(Plugin plugin);
    public Plugin getPluginCheckByFilename(String filename);

}
