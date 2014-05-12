/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginComponent derivate for report plugins
 * Inherited methods are documented in its parent.
 */

package checkit.plugin.component;

import checkit.server.domain.ContactDetail;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PluginReportComponent extends PluginComponentAbstract {
    @Autowired
    MessageSource messageSource;
    
    Locale locale = LocaleContextHolder.getLocale();

    /**
     * Send reports if monitoring service is up again.
     *
     * @param contactDetail List of contact details to send reports.
     * @param checkTitle Title of check to report.
     */
    @Async
    public void reportUp(List<ContactDetail> contactDetail, String checkTitle) {
        Object instance, params, result;
        for (ContactDetail item : contactDetail) {
            instance = getPluginInstance(item.getFilename());
            params = getCallParams(instance, item.getData());

            result = call(instance, "reportUp", checkTitle, params);
        }                
    }
    
    /**
     * Send reports if monitoring service is down.
     *
     * @param contactDetail List of contact details to send reports.
     * @param checkTitle Title of check to report.
     */
    @Async
    public void reportDown(List<ContactDetail> contactDetail, String checkTitle) {
        Object instance, params, result;
        for (ContactDetail item : contactDetail) {
            instance = getPluginInstance(item.getFilename());
            params = getCallParams(instance, item.getData());

            result = call(instance, "reportDown", checkTitle, params);
        }                
    }
    
    /**
     * Send regular reports.
     *
     * @param contactDetail List of contact details to send reports.
     * @param checkTitle Title of check to report.
     * @param numberOfDowns Number of times the service has been DOWN during last period.
     * @param timeOfDowns Time how long the service has been DOWN during last period.
     */
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

}
