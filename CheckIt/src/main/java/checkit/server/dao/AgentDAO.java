/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Agent;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface AgentDAO {
    public List<Agent> getAgentList();
    public void createAgent(Agent agent);
    public void deleteAgent(int agentId);
    public void updateAgent(Agent agent);
    public Agent getAgentById(int agentId);

}
