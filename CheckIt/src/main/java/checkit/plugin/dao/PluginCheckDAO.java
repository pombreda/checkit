/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginCheckDAO interface
 */

package checkit.plugin.dao;

import checkit.plugin.domain.PluginCheck;
import java.util.List;

public interface PluginCheckDAO {
    public List<PluginCheck> getPluginCheckList();
    public List<PluginCheck> getActivePluginCheckList();
    public void createPluginCheck(PluginCheck plugin);
    public void deletePluginCheck(String filename);
    public void updatePluginCheck(PluginCheck plugin);
    public PluginCheck getPluginCheckByFilename(String filename);

}
