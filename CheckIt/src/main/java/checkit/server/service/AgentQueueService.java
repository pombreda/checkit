/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.AgentQueue;
import checkit.server.domain.Testing;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface AgentQueueService {
    public List<AgentQueue> getAgentQueue();
    public void add(AgentQueue agentQueue);
    public void add(Testing test, String query);
    public void delete(int agentQueueId);
}
