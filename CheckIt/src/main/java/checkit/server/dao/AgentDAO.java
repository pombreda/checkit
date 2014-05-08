package checkit.server.dao;

import checkit.server.domain.Agent;
import java.util.List;

public interface AgentDAO {
    public List<Agent> getAgentList();
    public void createAgent(Agent agent);
    public void deleteAgent(int agentId);
    public void updateAgent(Agent agent);
    public Agent getAgentById(int agentId);
    public Agent getAgentByIp(String ip);
}
