/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginCheckService implementation
 */

package checkit.plugin.service;

import checkit.plugin.dao.PluginCheckDAO;
import checkit.plugin.domain.PluginCheck;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PluginCheckServiceImpl implements PluginCheckService {
    @Autowired
    private PluginCheckDAO pluginCheckDAO;

    /**
     * Register new check plugin
     *
     * @param filename Filename of new plugin
     */
    @Override
    public void registerPlugin(String filename) {
        PluginCheck report = new PluginCheck();
        report.setFilename(filename);
        report.setEnabled(false);
        report.setTitle(filename);
        report.setDescription(" ");
        
        pluginCheckDAO.createPluginCheck(report);
    }

    /**
     * Delete check plugin
     *
     * @param filename Filename of to delete
     */
    @Override
    public void deletePlugin(String filename) {
        pluginCheckDAO.deletePluginCheck(filename);
    }

    /**
     * Get check plugin by filename
     *
     * @param filename Filename of plugin to get
     *
     * @return PluginCheck or null if not exists.
     */
    @Override
    public PluginCheck getPluginByFilename(String filename) {
        return pluginCheckDAO.getPluginCheckByFilename(filename);
    }
    
    /**
     * Get the list of all check plugins
     *
     * @return List of all check plugins.
     */
    @Override
    public List<PluginCheck> getPluginList() {
        return pluginCheckDAO.getPluginCheckList();
    }

    /**
     * Get the list of all check plugins which are active
     *
     * @return List of all check plugins which are active.
     */
    @Override
    public List<PluginCheck> getActivePluginList() {
        return pluginCheckDAO.getActivePluginCheckList();
    }

    /**
     * Update check plugin
     *
     * @param pluginCheck PluginCheck to update, which already includes the updated data.
     */
    @Override
    public void updatePlugin(PluginCheck pluginCheck) {
        pluginCheckDAO.updatePluginCheck(pluginCheck);
    }

    
}
