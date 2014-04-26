/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.AgentQueue;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface AgentQueueDAO {
    public List<AgentQueue> getAgentQueue();
    public void add(AgentQueue agentQueue);
    public void delete(int agentQueueId);
}
