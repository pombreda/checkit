/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.Agent;
import checkit.server.domain.Test;
import checkit.server.domain.Testing;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NetworkServiceImpl implements NetworkService {
    @Autowired
    private AgentService agentService;

    @Autowired
    private TestService testService;

    @Scheduled(fixedDelay = 60000)
    public void sendAndEmptyQueue() {
        System.out.println("Spoustim vyprazdneni fronty v " + new Date());
        
    }
    
    @Async
    private boolean postRequestToAgent(Testing testing, String query) {
        Agent agent = agentService.getAgentById(testing.getAgentId());
        Test test = testService.getTestById(testing.getTestId());
        
        String ip = agent.getIp();
        int responseCode = 0;
        String responseMessage = "";
        
        try {
            URL url = new URL(ip + query);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);

            try (ObjectOutputStream out = new ObjectOutputStream(urlCon.getOutputStream())) {
                out.writeObject(test);
            }

            responseCode = urlCon.getResponseCode();
            responseMessage = urlCon.getResponseMessage();
        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworkServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (responseCode == 200 && responseMessage.equals("OK"));
    }
    
    @Override
    public void postCreatingToAgent(Testing testing) {
        if (!postRequestToAgent(testing, "create")) {
            System.out.println("Pridat do fronty");
        }
    }

    @Override
    public void postDeletingToAgent(Testing testing) {
        if (!postRequestToAgent(testing, "delete")) {
            System.out.println("Pridat do fronty");
        }
    }

    @Override
    public void postUpdatingToAgent(Testing testing) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
