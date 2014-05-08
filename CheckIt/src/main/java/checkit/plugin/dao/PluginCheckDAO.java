package checkit.plugin.dao;

import checkit.plugin.domain.Plugin;
import java.util.List;

public interface PluginCheckDAO {
    public List<Plugin> getPluginCheckList();
    public List<Plugin> getActivePluginCheckList();
    public void createPluginCheck(Plugin plugin);
    public void deletePluginCheck(String filename);
    public void updatePluginCheck(Plugin plugin);
    public Plugin getPluginCheckByFilename(String filename);

}
