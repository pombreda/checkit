/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentService interface
 */

package checkit.server.service;

import checkit.server.domain.Agent;
import java.util.List;

public interface AgentService {
    public List<Agent> getAgentList();
    public void createAgent(Agent agent);
    public void deleteAgent(int agentId);
    public void updateAgent(Agent agent);
    public Agent getAgentById(int agentId);
    public Agent getAgentByIp(String ip);
    public int getIdOfLeastBusyAgent();
}
