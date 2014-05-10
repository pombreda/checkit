/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ServerService interface
 */

package checkit.agent.service;

import checkit.server.domain.Server;
import java.util.List;

public interface ServerService {
    public List<Server> getServerList();
    public Server getServerWithTheHighestPriority();
    public Server getServerByIp(String ip);
}
