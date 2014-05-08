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

    //Classloader
    protected abstract String getPath();
    protected abstract String getClassPrefix();
    
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
    
    public String getInitEmptyDataJSON(List<Input> inputs) {
        JSONObject json = new JSONObject();
        for (Input row : inputs) {
            json.put(row.getId(), "");
        }
        return json.toJSONString();
    }

    protected Object getCallParams(Object instance, String data) {
        //returns values for plugin running
        Object paramsName = call(instance, "getCallRequiredParamsName", (Object[]) null);
        Object params = getValuesFromJSONString(data, paramsName);
        return params;
    }
    
    protected Object getValuesFromJSONString(String json, Object params) {
        //gives values of JSON names in params. E.g. name: Dodo, params is name, returns Dodo. - uses for give a data to run plugin
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

//Services
    public abstract void registerPlugin(String filename);
    public abstract void deletePlugin(String filename);
    public abstract void updatePlugin(Plugin plugin);
    public abstract Plugin getPluginByFilename(String filename);
    public abstract List<Plugin> getPluginList();
    public abstract List<Plugin> getActivePluginList();
}
