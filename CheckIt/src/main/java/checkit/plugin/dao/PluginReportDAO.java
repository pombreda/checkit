/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginReportDAO interface
 */

package checkit.plugin.dao;

import checkit.plugin.domain.PluginReport;
import java.util.List;

public interface PluginReportDAO {
    public List<PluginReport> getPluginReportList();
    public List<PluginReport> getActivePluginReportList();
    public void createPluginReport(PluginReport plugin);
    public void deletePluginReport(String filename);
    public void updatePluginReport(PluginReport plugin);
    public PluginReport getPluginReportByFilename(String filename);

}
