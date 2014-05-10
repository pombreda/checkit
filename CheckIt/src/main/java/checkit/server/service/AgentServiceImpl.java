/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentService implementation
 * All services related to agent
 */

package checkit.server.service;

import checkit.server.dao.AgentDAO;
import checkit.server.dao.CheckingDAO;
import checkit.server.domain.Agent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentDAO agentDAO;

    @Autowired
    private CheckingDAO checkingDAO;

    /**
     * Get the list of all agents
     *
     * @return List of all agents.
     */
    @Override
    public List<Agent> getAgentList() {
        return agentDAO.getAgentList();
    }

    /**
     * Create new agent
     *
     * @param agent Agent to create
     */
    @Override
    public void createAgent(Agent agent) {
        agentDAO.createAgent(agent);
    }

    /**
     * Delete agent
     *
     * @param agentId Id of agent to delete
     */
    @Override
    public void deleteAgent(int agentId) {
        agentDAO.deleteAgent(agentId);
    }

    /**
     * Update agent
     *
     * @param agent Agent to update, which already includes the updated data.
     */
    @Override
    public void updateAgent(Agent agent) {
        agentDAO.updateAgent(agent);
    }

    /**
     * Get agent by agent id
     *
     * @param agentId Id of agent to get
     *
     * @return Agent or null if not exists.
     */
    @Override
    public Agent getAgentById(int agentId) {
        return agentDAO.getAgentById(agentId);
    }

    /**
     * Get agent by agent ip
     *
     * @param ip Ip of agent to get
     *
     * @return Agent or null if not exists.
     */
    @Override
    public Agent getAgentByIp(String ip) {
        return agentDAO.getAgentByIp(ip);
    }

    /**
     * Get the id of least busy agent
     *
     * @return Id of agent with minimum checking.
     */
    @Override
    public int getIdOfLeastBusyAgent() {
        List<Agent> agents = agentDAO.getAgentList();

        int minId = agents.get(0).getAgentId();
        int minValue = checkingDAO.getCheckingByAgentId(minId).size();

        int currentValue;

        for (Agent agent : agents) {
            currentValue = checkingDAO.getCheckingByAgentId(agent.getAgentId()).size();
            if (currentValue < minValue) {
                minValue = currentValue;
                minId = agent.getAgentId();
            }
        }
        return minId;
    }
    
}
