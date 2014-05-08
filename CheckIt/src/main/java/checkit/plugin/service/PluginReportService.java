package checkit.plugin.service;

import checkit.plugin.dao.PluginReportDAO;
import checkit.server.domain.ContactDetail;
import checkit.plugin.domain.Plugin;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PluginReportService extends PluginServiceAbstract {
    @Autowired
    private PluginReportDAO pluginReportDAO;

    @Autowired
    MessageSource messageSource;
    
    Locale locale = LocaleContextHolder.getLocale();

    @Async
    public void reportUp(List<ContactDetail> contactDetail, String checkTitle) {
        Object instance, params, result;
        for (ContactDetail item : contactDetail) {
            instance = getPluginInstance(item.getFilename());
            params = getCallParams(instance, item.getData());

            result = call(instance, "reportUp", checkTitle, params);
        }                
    }
    
    @Async
    public void reportDown(List<ContactDetail> contactDetail, String checkTitle) {
        Object instance, params, result;
        for (ContactDetail item : contactDetail) {
            instance = getPluginInstance(item.getFilename());
            params = getCallParams(instance, item.getData());

            result = call(instance, "reportDown", checkTitle, params);
        }                
    }
    
    @Async
    public void reportRegular(List<ContactDetail> contactDetail, String checkTitle, int numberOfDowns, long timeOfDowns) {
        Object instance, params, result;
        for (ContactDetail item : contactDetail) {
            instance = getPluginInstance(item.getFilename());
            params = getCallParams(instance, item.getData());

            result = call(instance, "reportRegular", checkTitle, numberOfDowns, timeOfDowns, params);
        }                
    }
    
    @Override
    protected String getPath() {
        return messageSource.getMessage("path.plugin.report", null, locale);
    }

    @Override
    protected String getClassPrefix() {
        return "checkit.plugin.report";
    }

    @Override
    public void registerPlugin(String filename) {
        Plugin report = new Plugin();
        report.setFilename(filename);
        report.setEnabled(false);
        report.setTitle(filename);
        report.setDescription(" ");
        
        pluginReportDAO.createPluginReport(report);
    }

    @Override
    public void deletePlugin(String filename) {
        pluginReportDAO.deletePluginReport(filename);
    }

    @Override
    public Plugin getPluginByFilename(String filename) {
        return pluginReportDAO.getPluginReportByFilename(filename);
    }
    
    @Override
    public List<Plugin> getPluginList() {
        return pluginReportDAO.getPluginReportList();
    }

    @Override
    public List<Plugin> getActivePluginList() {
        return pluginReportDAO.getActivePluginReportList();
    }

    @Override
    public void updatePlugin(Plugin plugin) {
        pluginReportDAO.updatePluginReport(plugin);
    }

}
