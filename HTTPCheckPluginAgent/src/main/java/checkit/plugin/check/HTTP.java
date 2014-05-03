/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.check;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dodo
 */
public class HTTP implements CheckAgent {

    @Override
    public Object getCallRequiredParamsName() {
        return new Object[]{"url", "followRedirects"};
    }

    @Override
    public Object getResultParamsName() {
        return new Object[]{"code", "delay"};
    }

    @Override
    public Object isItOk(Object[] params) {
        //receives values for params just as it was send from run(), in Object type
        int code = (int) params[0];
        return code < 400 && code != -1;
    }

    @Override
    public Object run(Object[] params) {
        //receives values for params from getCallRequiredParamsName(), in Object type
        String url = params[0].toString();
        boolean followRedirects = Boolean.parseBoolean(params[1].toString());
        
        int responseCode = -1;
        int elapsedTime = -1;
        
        if (followRedirects) {
            System.setProperty("http.maxRedirects", "10");
        }
        
        try {
            long startTime = System.currentTimeMillis();
            HttpURLConnection con = (HttpURLConnection)(new URL(url).openConnection());
            con.setInstanceFollowRedirects(followRedirects);
            con.setConnectTimeout(5000);
            con.setRequestMethod("HEAD");
            con.connect();
            elapsedTime = (int)(System.currentTimeMillis() - startTime);
            responseCode = con.getResponseCode();
            if (responseCode >= 400) elapsedTime = 0;
//            String location = con.getHeaderField("Location"); //location of redirect
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTTP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Object[]{
            responseCode,
            elapsedTime
        };
    }

}
