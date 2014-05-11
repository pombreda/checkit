/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginComponent
 */

package checkit.agent.component;

import checkit.agent.service.ResultService;
import checkit.server.domain.Result;
import checkit.server.domain.Check;
import java.beans.Expression;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PluginComponent {
    private Map<String, Object> pluginInstance = new HashMap<String, Object>();

    @Autowired
    private ResultService resultService;

    @Autowired
    MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();
    
    /**
     * Run check, evaluate the result and add result to queue for servers.
     * 
     * @param check Check to test.
     */
    @Async
    public void runCheckAndSaveResult(Check check) {
        Object instance = getPluginInstance(check.getFilename());
        Object params = getCallParams(instance, check.getData());
        Object resultParams = call(instance, "getResultParamsName", (Object[]) null);
        Object resultValues = call(instance, "run", params);

        String status;
        if (Boolean.parseBoolean(call(instance, "isItOk", resultValues).toString())) {
            status = "U";
        } else {
            status = "D";
        }
            
        String resultJSON = createJSONStringFromResults(resultParams, resultValues);

        Result result = new Result();
        result.setCheckId(check.getCheckId());
        result.setStatus(status);
        result.setData(resultJSON);
        resultService.createResult(result);
    }
    
    /**
     * Get values of parameters required by plugin to run.
     * 
     * @param instance Instance of loaded plugin.
     * @param data JSON in string with user custom setting.
     * 
     * @return Always return ARRAY of objects
     */
    private Object getCallParams(Object instance, String data) {
        //returns values for check running
        Object paramsName = call(instance, "getCallRequiredParamsName", (Object[]) null);
        Object params = getValuesFromJSONString(data, paramsName);
        return params;
    }
    
    /**
     * Create JSON string from two objects arrays.
     * For example: ["color", "shape", "value"] and ["red", "rectangle", 2] create {"color": "red", "shape": "rectangle", "value": 2}
     * 
     * @param p Array of parameters names.
     * @param v Array of values.
     * 
     * @return Always return ARRAY of objects
     */
    private String createJSONStringFromResults(Object p, Object v) {
        //receive params names a params values and from them JSON string
        Object[] params = (Object[]) p;
        Object[] values = (Object[]) v;
        JSONObject json = new JSONObject();
        
        for (int i = 0; i < params.length && i < values.length; i++) {
            json.put(params[i], values[i]);
        }
        return json.toJSONString();
    }
    
    /**
     * Give values of names in JSON.
     * E.g. for {"color": "red"}, params is "color", returns "red".
     * 
     * @param json Instance of loaded plugin.
     * @param params JSON in string with user custom setting.
     * 
     * @return Always return ARRAY of objects
     */
    private Object getValuesFromJSONString(String json, Object params) {
        List values = new ArrayList();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            for (Object param: (Object[]) params) {
                values.add(jsonObject.get(param));
            }
        } catch (ParseException ex) {
            Logger.getLogger(PluginComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return values.toArray();
    }
    
    /**
     * Load a new class from external file by Java Classloader, if it is not already in memory.
     * 
     * @param filename Filename of wanted plugin
     * 
     * @return Instance of plugin
     */
    private Object getPluginInstance(String filename) {
        //TODO - this is really awful and painful. ASAP rewrite to OSGi or any other framework
        Object instance = pluginInstance.get(filename);
        if (instance == null) {
            try {
                File file  = new File(messageSource.getMessage("path.plugin.agent", null, locale) + filename + ".jar");
                URL url = file.toURI().toURL();
                URL[] urls = new URL[]{url};

                ClassLoader loader = URLClassLoader.newInstance(
                    urls,
                    getClass().getClassLoader()
                );
                Class<?> cls = Class.forName("checkit.plugin.check." + filename, true, loader);

                instance = cls.newInstance();
                pluginInstance.put(filename, instance);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException ex) {
                Logger.getLogger(PluginComponent.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        }
        return instance;
    }
    
    /**
     * Call method of dynamic loaded class
     * 
     * @param instance Instance of loaded plugin
     * @param methodName Method name to call
     * @param params Optional parameters for calling method. If method has no parameters, call (Object[]) null
     * 
     * @return Results of called method.
     */
    private Object call(Object instance, String methodName, Object... params) {
        Expression expr = new Expression(instance, methodName, params) {};
        Object result = null;
        try {
            result = expr.getValue();
        } catch (Exception ex) {
            Logger.getLogger(PluginComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
