/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    @Override
    public List<Server> getServerList() {
        return serverDAO.getServerList();
    }

    @Override
    public Server getServerWithTheHighestPriority() {
        return serverDAO.getServerWithTheHighestPriority();
    }
    @Override
    public Server getServerByIp(String ip) {
        return serverDAO.getServerByIp(ip);
    }

}
