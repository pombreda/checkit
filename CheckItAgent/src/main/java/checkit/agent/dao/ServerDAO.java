package checkit.agent.dao;

import checkit.server.domain.Server;
import java.util.List;

public interface ServerDAO {
    public List<Server> getServerList();
    public Server getServerWithTheHighestPriority();
    public Server getServerByIp(String ip);
}
