package checkit.plugin.service;

import checkit.plugin.dao.PluginCheckDAO;
import checkit.plugin.domain.Plugin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PluginCheckService extends PluginServiceAbstract {
    @Autowired
    private PluginCheckDAO pluginCheckDAO;

    @Autowired
    MessageSource messageSource;
    
    Locale locale = LocaleContextHolder.getLocale();

    @Override
    protected String getPath() {
        return messageSource.getMessage("path.plugin.check", null, locale);
    }

    @Override
    protected String getClassPrefix() {
        return "checkit.plugin.check";
    }

    @Override
    public void registerPlugin(String filename) {
        Plugin report = new Plugin();
        report.setFilename(filename);
        report.setEnabled(false);
        report.setTitle(filename);
        report.setDescription(" ");
        
        pluginCheckDAO.createPluginCheck(report);
    }

    @Override
    public void deletePlugin(String filename) {
        pluginCheckDAO.deletePluginCheck(filename);
    }

    @Override
    public Plugin getPluginByFilename(String filename) {
        return pluginCheckDAO.getPluginCheckByFilename(filename);
    }
    
    @Override
    public List<Plugin> getPluginList() {
        return pluginCheckDAO.getPluginCheckList();
    }

    @Override
    public List<Plugin> getActivePluginList() {
        return pluginCheckDAO.getActivePluginCheckList();
    }

    @Override
    public void updatePlugin(Plugin plugin) {
        pluginCheckDAO.updatePluginCheck(plugin);
    }

    public List<String> getTableHeader(String pluginName) {
        Object instance = getPluginInstance(pluginName);
        Object[] header = (Object[]) call(instance, "getTableRequiredHeaderTitle", (Object[]) null);
        List<String> output = new ArrayList<String>();
        if (header != null) {
            for (Object item : header) {
                output.add(item.toString());
            }
        }
        return output;
    }
    
    public List< List<Object> > getTableValues(String pluginName, List<String> data) {
        Object instance = getPluginInstance(pluginName);
        Object params = call(instance, "getTableRequiredParamsName", (Object[]) null);
        List< List<Object> > output = new ArrayList<List<Object>>();
        Object[] values;
        if (params != null) {
            for (int i = 0; i<data.size(); i++) {
                values = (Object[]) getValuesFromJSONString(data.get(i), params);
                output.add(new ArrayList<Object>(Arrays.asList(values)));
            }
        }
        return output;
    }
}
