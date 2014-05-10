/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginReportDAO interface
 */

package checkit.plugin.dao;

import checkit.plugin.domain.Plugin;
import java.util.List;

public interface PluginReportDAO {
    public List<Plugin> getPluginReportList();
    public List<Plugin> getActivePluginReportList();
    public void createPluginReport(Plugin plugin);
    public void deletePluginReport(String filename);
    public void updatePluginReport(Plugin plugin);
    public Plugin getPluginReportByFilename(String filename);

}
