/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentQueueService implementation
 * All services related to agent queue
 */

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
    
    /**
     * Get the list of all items in queue
     *
     * @return List of all items in queue.
     */
    @Override
    public List<AgentQueue> getAgentQueue() {
        return queue.getAgentQueue();
    }

    /**
     * Add new item into the queue
     *
     * @param agentQueue Item to add
     */
    @Override
    public void add(AgentQueue agentQueue) {
        queue.add(agentQueue);
    }

    /**
     * Add new item into the queue
     *
     * @param checking Checking to add
     * @param query Query to add
     */
    @Override
    public void add(Checking checking, String query) {
        AgentQueue agentQueue = new AgentQueue();
        agentQueue.setAgentId(checking.getAgentId());
        agentQueue.setCheckId(checking.getCheckId());
        agentQueue.setQuery(query);
        add(agentQueue);
    }
    
    /**
     * Delete item from in the queue
     *
     * @param agentQueueId Id of item to delete
     */
    @Override
    public void delete(int agentQueueId) {
        queue.delete(agentQueueId);
    }

}
