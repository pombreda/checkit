/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The NetworkService implementation
 * All services related to communication with agents
 */

package checkit.server.service;

import checkit.server.domain.Agent;
import checkit.server.domain.AgentQueue;
import checkit.server.domain.Check;
import checkit.server.domain.Checking;
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

@Service
public class NetworkServiceImpl implements NetworkService {
    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentQueueService agentQueueService;

    @Autowired
    private CheckService checkService;

    /**
     * Cron, send every minute queue to agents.
     *
     */
    @Scheduled(fixedDelay = 60000)
    public void sendAndEmptyQueue() {
        List<AgentQueue> queue = agentQueueService.getAgentQueue();
        Checking checking = new Checking();
        for (AgentQueue item : queue) {
            checking.setCheckId(item.getCheckId());
            checking.setAgentId(item.getAgentId());
            if (postRequestToAgent(checking, item.getQuery())) {
                agentQueueService.delete(item.getAgentQueueId());
            }
        }
    }
    
    /**
     * Send request to agent.
     *
     * @param checking Checking for getting more details.
     * @param query Type of request (e.g. create, delete, update).
     */
    @Async
    private boolean postRequestToAgent(Checking checking, String query) {
        Agent agent = agentService.getAgentById(checking.getAgentId());
        Check check = checkService.getCheckById(checking.getCheckId());
        
        String ip = agent.getPostAddress();
        int responseCode = 0;
        String responseMessage = "";
        
        try {
            URL url = new URL(ip + query);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);

            try (ObjectOutputStream out = new ObjectOutputStream(urlCon.getOutputStream())) {
                out.writeObject(check);
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
    
    /**
     * Send a request to create
     *
     * @param checking Checking for getting more details.
     */
    @Override
    public void postCreatingToAgent(Checking checking) {
        if (!postRequestToAgent(checking, "create")) {
            agentQueueService.add(checking, "create");
        }
    }

    /**
     * Send a request to delete
     *
     * @param checking Checking for getting more details.
     */
    @Override
    public void postDeletingToAgent(Checking checking) {
        if (!postRequestToAgent(checking, "delete")) {
            agentQueueService.add(checking, "delete");
        }
    }

    /**
     * Send a request to update
     *
     * @param checking Checking for getting more details.
     */
    @Override
    public void postUpdatingToAgent(Checking checking) {
        if (!postRequestToAgent(checking, "update")) {
            agentQueueService.add(checking, "update");
        }
    }
    
}
