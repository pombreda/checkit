/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginReportService interface
 */

package checkit.plugin.service;

import checkit.plugin.domain.PluginReport;
import java.util.List;

public interface PluginReportService {
    public void registerPlugin(String filename);
    public void deletePlugin(String filename);
    public void updatePlugin(PluginReport plugin);
    public PluginReport getPluginByFilename(String filename);
    public List<PluginReport> getPluginList();
    public List<PluginReport> getActivePluginList();    
}
