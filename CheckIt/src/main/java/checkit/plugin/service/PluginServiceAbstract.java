/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.service;

import checkit.plugin.domain.FormStruct;
import checkit.plugin.domain.FormStructRow;
import checkit.server.domain.Plugin;
import java.beans.Expression;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Dodo
 */
public abstract class PluginServiceAbstract {

    //Classloader
    protected abstract String getPath();
    protected abstract String getClassPrefix();
    
    public Object getPluginInstance(String filename) {
        //TODO - this is really awful and painful. ASAP rewrite to OSGi or any other framework
        Object instance = null;
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException ex) {
            Logger.getLogger(PluginReportService.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List< List<String> > getInputs(Object instance) {
        String jsonString = call(instance, "getOptionsJSON", (Object[]) null).toString();
        List< List<String> > json = new ArrayList<List<String>>();
        
        try {
            JSONParser parser = new JSONParser();
            
            JSONArray inputs = (JSONArray)((JSONObject) parser.parse(jsonString)).get("inputs");
            Iterator<JSONObject> iterator = inputs.iterator();
            while (iterator.hasNext()) {
                final JSONObject obj = iterator.next();
                json.add(new ArrayList<String>() {{
                    add(obj.get("id").toString());
                    add(obj.get("type").toString());
                    add(obj.get("title").toString());
                    add(obj.get("description").toString());
                }});
            }
        } catch (ParseException ex) {
            Logger.getLogger(PluginServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return json;
    }
    
    public FormStruct getInputValues(List< List<String> > inputs, String data) {
        FormStruct formStruct = new FormStruct();
        ArrayList<FormStructRow> rows = new ArrayList<FormStructRow>();
        String currentId;
        try {
            JSONParser parser = new JSONParser();
            JSONObject dataJson;
            dataJson = (JSONObject) parser.parse(data);
            for(List<String> input : inputs) {
                currentId = input.get(0);
                rows.add(new FormStructRow(currentId, dataJson.get(currentId).toString(), input.get(1)));
            }
            formStruct.setArrData(rows);
        } catch (ParseException ex) {
            Logger.getLogger(PluginServiceAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formStruct;
    }
    
    public String getInitEmptyDataJSON(List< List<String> > inputs) {
        JSONObject json = new JSONObject();
        for (List<String> row : inputs) {
            json.put(row.get(0), "");
        }
        return json.toJSONString();
    }

    //Services
    public abstract void registerPlugin(String filename);
    public abstract void deletePlugin(String filename);
    public abstract void updatePlugin(Plugin plugin);
    public abstract Plugin getPluginByFilename(String filename);
    public abstract List<Plugin> getPluginList();
    public abstract List<Plugin> getActivePluginList();
}
