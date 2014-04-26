/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.dao.AgentQueueDAO;
import checkit.server.domain.AgentQueue;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentQueueServiceImpl implements AgentQueueService {
    @Autowired
    private AgentQueueDAO queue;
    
    @Override
    public List<AgentQueue> getAgentQueue() {
        return queue.getAgentQueue();
    }

    @Override
    public void add(AgentQueue agentQueue) {
        queue.add(agentQueue);
    }

    @Override
    public void delete(int agentQueueId) {
        queue.delete(agentQueueId);
    }
    
}
