package checkit.server.dao;

import checkit.server.domain.AgentQueue;
import java.util.List;

public interface AgentQueueDAO {
    public List<AgentQueue> getAgentQueue();
    public void add(AgentQueue agentQueue);
    public void delete(int agentQueueId);
}
