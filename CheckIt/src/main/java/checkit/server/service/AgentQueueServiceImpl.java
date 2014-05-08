package checkit.server.service;

import checkit.server.dao.AgentQueueDAO;
import checkit.server.domain.AgentQueue;
import checkit.server.domain.Checking;
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
    public void add(Checking checking, String query) {
        AgentQueue agentQueue = new AgentQueue();
        agentQueue.setAgentId(checking.getAgentId());
        agentQueue.setCheckId(checking.getCheckId());
        agentQueue.setQuery(query);
        add(agentQueue);
    }
    
    @Override
    public void delete(int agentQueueId) {
        queue.delete(agentQueueId);
    }

}
