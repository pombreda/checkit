/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentQueueService interface
 */

package checkit.server.service;

import checkit.server.domain.AgentQueue;
import checkit.server.domain.Checking;
import java.util.List;

public interface AgentQueueService {
    public List<AgentQueue> getAgentQueue();
    public void add(AgentQueue agentQueue);
    public void add(Checking checking, String query);
    public void delete(int agentQueueId);
}
