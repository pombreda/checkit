package checkit.server.service;

import checkit.server.dao.AgentDAO;
import checkit.server.domain.Agent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentDAO agentDAO;

    @Override
    public List<Agent> getAgentList() {
        return agentDAO.getAgentList();
    }

    @Override
    public void createAgent(Agent agent) {
        agentDAO.createAgent(agent);
    }

    @Override
    public void deleteAgent(int agentId) {
        agentDAO.deleteAgent(agentId);
    }

    @Override
    public void updateAgent(Agent agent) {
        agentDAO.updateAgent(agent);
    }

    @Override
    public Agent getAgentById(int agentId) {
        return agentDAO.getAgentById(agentId);
    }

    @Override
    public Agent getAgentByIp(String ip) {
        return agentDAO.getAgentByIp(ip);
    }
    
}
