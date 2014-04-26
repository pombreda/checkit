/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.jdbc;

import checkit.server.domain.Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class ServerExtractor  implements ResultSetExtractor<Server> {
    @Override
    public Server extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Server server = new Server();
        server.setIp(resultSet.getString(1));
        server.setPriority(resultSet.getInt(2));
        return server;
    }
}
