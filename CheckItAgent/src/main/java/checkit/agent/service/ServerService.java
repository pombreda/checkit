/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import checkit.server.domain.Server;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ServerService {
    public List<Server> getServerList();
    public Server getServerWithTheHighestPriority();
    public Server getServerByIp(String ip);
}
