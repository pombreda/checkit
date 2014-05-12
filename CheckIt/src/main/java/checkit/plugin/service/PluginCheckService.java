/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginCheckService interface
 */

package checkit.plugin.service;

import checkit.plugin.domain.PluginCheck;
import java.util.List;

public interface PluginCheckService {
    public void registerPlugin(String filename);
    public void deletePlugin(String filename);
    public void updatePlugin(PluginCheck plugin);
    public PluginCheck getPluginByFilename(String filename);
    public List<PluginCheck> getPluginList();
    public List<PluginCheck> getActivePluginList();
}
