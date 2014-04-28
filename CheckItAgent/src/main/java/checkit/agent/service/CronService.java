/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import checkit.server.domain.Result;
import checkit.server.domain.Server;
import checkit.server.domain.Test;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dodo
 */
@Service
public class CronService {
    @Autowired
    private TestService testService;
    
    @Autowired
    private ServerService serverService;
    
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private PluginService pluginService;
    
    private final int minInterval = 15;
    private final int maxInterval = 3600;
    
    private int timeCounter = minInterval;
    
    private void setTimeCounter() {
        timeCounter += minInterval;
        if (timeCounter > maxInterval) {
            timeCounter = minInterval;
        }
    }
    
    @Scheduled(fixedDelay = minInterval * 1000)
    public void Cron() {
        List<Test> testList = testService.getTestList();
        
        for (Test test : testList) {
            if (timeCounter % test.getInterval() == 0) {
                pluginService.runTestAndSaveResult(test);
            }
        }

        setTimeCounter();
    }

    @Scheduled(fixedDelay = minInterval * 1000)
    public void SendAndEmptyResults() {
        List<Result> resultList = resultService.getResultList();
        
        for (Result result : resultList) {
            if (postRequestToServer(result)) {
                resultService.deleteResult(result);
            }
        }

    }
    
    @Async
    private boolean postRequestToServer(Result result) {
        Server server = serverService.getServerWithTheHighestPriority();
        int responseCode = 0;
        String responseMessage = "";
        
        try {
            URL url = new URL(server.getPostAddress() + "postResult");
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);

            try (ObjectOutputStream out = new ObjectOutputStream(urlCon.getOutputStream())) {
                out.writeObject(result);
            }

            responseCode = urlCon.getResponseCode();
            responseMessage = urlCon.getResponseMessage();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CronService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CronService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (responseCode == 200 && responseMessage.equals("OK"));
    }

}
