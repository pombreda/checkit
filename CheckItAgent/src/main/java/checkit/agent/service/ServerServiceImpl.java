/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ServerService implementation
 * All services related to server
 */

package checkit.agent.service;

import checkit.agent.dao.ServerDAO;
import checkit.server.domain.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerDAO serverDAO;

    /**
     * Get the list of all servers
     *
     * @return List of all servers.
     */
    @Override
    public List<Server> getServerList() {
        return serverDAO.getServerList();
    }

    /**
     * Get the row (server) with highest priority
     *
     * @return Server with highest priority (first, if found more) or null if not exists.
     */
    @Override
    public Server getServerWithTheHighestPriority() {
        return serverDAO.getServerWithTheHighestPriority();
    }

    /**
     * Get server by ip
     *
     * @param ip Ip of server to get
     *
     * @return Server or null if not exists.
     */
    @Override
    public Server getServerByIp(String ip) {
        return serverDAO.getServerByIp(ip);
    }

}
