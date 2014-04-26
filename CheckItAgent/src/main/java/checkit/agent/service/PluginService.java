/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import java.beans.Expression;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class PluginService {

    public Object getPluginInstance(String filename) {
        //TODO - this is really awful and painful. ASAP rewrite to OSGi or any other framework
        Object instance = null;
        try {
            File file  = new File("D:\\Skola\\CVUT\\bakule\\! zdrojak\\plugins\\agent\\" + filename + ".jar");
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader loader = URLClassLoader.newInstance(
                urls,
                getClass().getClassLoader()
            );
            Class<?> cls = Class.forName("checkit.plugin.check." + filename, true, loader);

            instance = cls.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException ex) {
            Logger.getLogger(PluginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }
    
    public Object call(Object instance, String methodName, Object... params) {
        Expression expr = new Expression(instance, methodName, params) {};
        Object result = null;
        try {
            result = expr.getValue();
        } catch (Exception ex) {
            Logger.getLogger(PluginService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
