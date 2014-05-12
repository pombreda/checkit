/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginReportService implementation
 */

package checkit.plugin.service;

import checkit.plugin.dao.PluginReportDAO;
import checkit.plugin.domain.PluginReport;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PluginReportServiceImpl implements PluginReportService {
    @Autowired
    private PluginReportDAO pluginReportDAO;

    @Override
    /**
     * Register new report plugin
     *
     * @param filename Filename of new plugin
     */
    public void registerPlugin(String filename) {
        PluginReport report = new PluginReport();
        report.setFilename(filename);
        report.setEnabled(false);
        report.setTitle(filename);
        report.setDescription(" ");
        
        pluginReportDAO.createPluginReport(report);
    }

    /**
     * Delete report plugin
     *
     * @param filename Filename of plugin to delete
     */
    @Override
    public void deletePlugin(String filename) {
        pluginReportDAO.deletePluginReport(filename);
    }

    /**
     * Get report plugin by filename
     *
     * @param filename Filename of plugin to get
     *
     * @return PluginReport or null if not exists.
     */
    @Override
    public PluginReport getPluginByFilename(String filename) {
        return pluginReportDAO.getPluginReportByFilename(filename);
    }
    
    /**
     * Get the list of all report plugins
     *
     * @return List of all report plugins.
     */
    @Override
    public List<PluginReport> getPluginList() {
        return pluginReportDAO.getPluginReportList();
    }

    /**
     * Get the list of all report plugins which are active
     *
     * @return List of all report plugins which are active.
     */
    @Override
    public List<PluginReport> getActivePluginList() {
        return pluginReportDAO.getActivePluginReportList();
    }

    /**
     * Update report plugin
     *
     * @param pluginReport PluginReport to update, which already includes the updated data.
     */
    @Override
    public void updatePlugin(PluginReport pluginReport) {
        pluginReportDAO.updatePluginReport(pluginReport);
    }

    
}
