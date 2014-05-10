/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "servers" database table row mapper
 */

package checkit.agent.jdbc;

import checkit.server.domain.Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ServerRowMapper implements RowMapper<Server> {

    /**
     * Get server from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Server from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Server mapRow(ResultSet resultSet, int line) throws SQLException {
        ServerExtractor serverExtractor = new ServerExtractor();
        return serverExtractor.extractData(resultSet);
    }
    
}
