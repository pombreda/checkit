/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginService abstract class
 */

package checkit.plugin.service;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.FormStructRow;
import checkit.plugin.domain.Input;
import checkit.plugin.domain.Plugin;
import java.beans.Expression;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class PluginServiceAbstract {
    private Map<String, Object> pluginInstance = new HashMap<String, Object>();

    /**
     * Get path to folder with plugins files
     * 
     * @return Path to the folder
     */
    protected abstract String getPath();

    /**
     * Get package name of plugins class
     * 
     * @return Package name of plugins class
     */
    protected abstract String getClassPrefix();
    
    /**
     * Load a new class from external file by Java Classloader, if it is not already in memory.
     * 
     * @param filename Filename of wanted plugin
     * 
     * @return Instance of plugin
     */
    public Object getPluginInstance(String filename) {
        //TODO - this is really awful and painful. ASAP rewrite to OSGi or any other framework
        Object instance = pluginInstance.get(filename);
        if (instance == null) {
            try {
                File file  = new File(getPath() + filename + ".jar");
                URL url = file.toURI().toURL();
                URL[] urls = new URL[]{url};

                ClassLoader loader = URLClassLoader.newInstance(
                    urls,
                    getClass().getClassLoader()
                );
                Class<?> cls = Class.forName(getClassPrefix() + "." + filename, true, loader);

                instance = cls.newInstance();                
                pluginInstance.put(filename, instance);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException ex) {
                Logger.getLogger(PluginReportService.class.getName()).log(Level.SEVERE, null, ex);
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
    public Object call(Object instance, String methodName, Object... params) {
        Expression expr = new Expression(instance, methodName, params);
        Object result = null;
        try {
            result = expr.getValue();
        } catch (Exception ex) {
            Logger.getLogger(PluginReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Create inputs for customizing the plugin.
     * All these data is required for plugin running.
     * 
     * @param instance Instance of loaded plugin
     * 
     * @return List of inputs.
     */
    public List<Input> getInputs(Object instance) {
        String jsonString = call(instance, "getOptionsJSON", (Object[]) null).toString();
        List<Input> json = new ArrayList<Input>();
        
        try {
            JSONParser parser = new JSONParser();
            
            JSONArray inputs = (JSONArray)((JSONObject) parser.parse(jsonString)).get("inputs");
            JSONArray options;
            List<String> optionsList = new ArrayList<String>();
            Input input;
            Iterator<JSONObject> iterator = inputs.iterator();
            while (iterator.hasNext()) {
                final JSONObject obj = iterator.next();
                input = new Input();
                input.setId(obj.get("id").toString());
                input.setType(obj.get("type").toString());
                input.setTitle(obj.get("title").toString());
                input.setDescription(obj.get("description").toString());
                options = (JSONArray) obj.get("options");
                
                if (options != null) {
                    Iterator<String> option = options.iterator();
                    while (option.hasNext()) {
                        optionsList.add(option.next());
                    }
                    input.setOptions(optionsList);
                } else {
                    input.setOptions(null);
                }
                json.add(input);
            }
        } catch (ParseException ex) {
            Logger.getLogger(PluginServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return json;
    }
    
    /**
     * Initialize inputs by default values based on previous settings.
     * If user already filled settings, this function loads these values to inputs.
     * 
     * @param inputs List of inputs to initialize by previous settings.
     * @param data Data with custom settings
     * 
     * @return Complete form struct for view layer.
     */
    public FormStruct getInputValues(List<Input> inputs, String data) {
        FormStruct formStruct = new FormStruct();
        ArrayList<FormStructRow> rows = new ArrayList<FormStructRow>();
        String currentId;
        try {
            JSONParser parser = new JSONParser();
            JSONObject dataJson;
            dataJson = (JSONObject) parser.parse(data);
            for(Input input : inputs) {
                currentId = input.getId();
                rows.add(new FormStructRow(currentId, dataJson.get(currentId).toString(), input.getType()));
            }
            formStruct.setArrData(rows);
        } catch (ParseException ex) {
            Logger.getLogger(PluginServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formStruct;
    }
    
    /**
     * Initialize inputs by empty values.
     * 
     * @param inputs List of inputs to initialize.
     * 
     * @return JSON in string with empty data required by plugin
     */
    public String getInitEmptyDataJSON(List<Input> inputs) {
        JSONObject json = new JSONObject();
        for (Input row : inputs) {
            json.put(row.getId(), "");
        }
        return json.toJSONString();
    }

    /**
     * Get values of parameters required by plugin to run.
     * 
     * @param instance Instance of loaded plugin.
     * @param data JSON in string with user custom setting.
     * 
     * @return Always return ARRAY of objects
     */
    protected Object getCallParams(Object instance, String data) {
        //returns values for plugin running
        Object paramsName = call(instance, "getCallRequiredParamsName", (Object[]) null);
        Object params = getValuesFromJSONString(data, paramsName);
        return params;
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
    protected Object getValuesFromJSONString(String json, Object params) {
        List values = new ArrayList();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            for (Object param: (Object[]) params) {
                values.add(jsonObject.get(param));
            }
        } catch (ParseException | NullPointerException ex) {
            for (Object param: (Object[]) params) {
                values.add("");
            }
        }
        return values.toArray();
    }

    /**
     * Register new plugin
     *
     * @param filename Filename of new plugin
     */
    public abstract void registerPlugin(String filename);

    /**
     * Delete plugin
     *
     * @param filename Filename of plugin to delete
     */
    public abstract void deletePlugin(String filename);

    /**
     * Update plugin
     *
     * @param plugin Plugin to update, which already includes the updated data.
     */
    public abstract void updatePlugin(Plugin plugin);

    /**
     * Get plugin by filename
     *
     * @param filename Filename of plugin to get
     *
     * @return Plugin or null if not exists.
     */
    public abstract Plugin getPluginByFilename(String filename);
    
    /**
     * Get the list of all plugins
     *
     * @return List of all plugins.
     */
    public abstract List<Plugin> getPluginList();
    
    /**
     * Get the list of all plugins which are active
     *
     * @return List of all plugins which are active.
     */
    public abstract List<Plugin> getActivePluginList();
}
