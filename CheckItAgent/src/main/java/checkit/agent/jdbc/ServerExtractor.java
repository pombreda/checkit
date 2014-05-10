/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "servers" database table row extractor
 */

package checkit.agent.jdbc;

import checkit.server.domain.Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ServerExtractor  implements ResultSetExtractor<Server> {
    
    /**
     * Extract appropriate database table row into the class Server
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted server from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Server extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Server server = new Server();
        server.setIp(resultSet.getString(1));
        server.setPostAddress(resultSet.getString(2));
        server.setPriority(resultSet.getInt(3));
        return server;
    }
}
