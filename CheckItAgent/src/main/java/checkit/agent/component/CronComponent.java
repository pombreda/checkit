/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * Cron service.
 */

package checkit.agent.component;

import checkit.agent.service.CheckService;
import checkit.agent.service.ResultService;
import checkit.agent.service.ServerService;
import checkit.server.domain.Result;
import checkit.server.domain.Server;
import checkit.server.domain.Check;
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
import org.springframework.stereotype.Component;

@Component
public class CronComponent {
    @Autowired
    private CheckService checkService;
    
    @Autowired
    private ServerService serverService;
    
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private PluginComponent pluginService;
    
    private final int minInterval = 15;
    private final int maxInterval = 3600;
    
    private int timeCounter = minInterval;
    
    /**
     * Count time to run right checkings. After reach maximum, reset to min value.
     *
     */
    private void setTimeCounter() {
        timeCounter += minInterval;
        if (timeCounter > maxInterval) {
            timeCounter = minInterval;
        }
    }
    
    /**
     * Run all the tests to be run at any given moment.
     *
     */
    @Scheduled(fixedDelay = minInterval * 1000)
    public void Cron() {
        List<Check> checkList = checkService.getCheckList();
        
        for (Check check : checkList) {
            if (timeCounter % check.getInterval() == 0) {
                pluginService.runCheckAndSaveResult(check);
            }
        }

        setTimeCounter();
    }

    /**
     * Send ever mininimal interval queue to servers.
     * Delete item from queue, if everything is ok.
     *
     */
    @Scheduled(fixedDelay = minInterval * 1000)
    public void SendAndEmptyResults() {
        List<Result> resultList = resultService.getResultList();
        
        for (Result result : resultList) {
            if (postRequestToServer(result)) {
                resultService.deleteResult(result);
            }
        }

    }
    
    /**
     * Send reslts to server.
     *
     * @param result Result to send.
     */
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
            Logger.getLogger(CronComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CronComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (responseCode == 200 && responseMessage.equals("OK"));
    }

}
