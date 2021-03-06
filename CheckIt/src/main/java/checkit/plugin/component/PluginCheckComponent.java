/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginComponent derivate for check plugins
 * Inherited methods are documented in its parent.
 */

package checkit.plugin.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PluginCheckComponent extends PluginComponentAbstract {
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

    /**
     * Get header of additional information for table in result page.
     *
     * @param filename Filename of plugin.
     * 
     * @return List of strings with table header titles.
     */
    public List<String> getTableHeader(String filename) {
        Object instance = getPluginInstance(filename);
        Object[] header = (Object[]) call(instance, "getTableRequiredHeaderTitle", (Object[]) null);
        List<String> output = new ArrayList<String>();
        if (header != null) {
            for (Object item : header) {
                output.add(item.toString());
            }
        }
        return output;
    }
    
    /**
     * Get values for additional information for table in result page.
     *
     * @param filename Filename of plugin.
     * @param data List of result display.
     * 
     * @return List of lists of objects (table) of additional data to display in table.
     */
    public List< List<Object> > getTableValues(String filename, List<String> data) {
        Object instance = getPluginInstance(filename);
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
