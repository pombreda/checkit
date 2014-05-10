/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "agents" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.Agent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class AgentExtractor implements ResultSetExtractor<Agent> {
    
    /**
     * Extract appropriate database table row into the class Agent
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted agent from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Agent extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Agent agent = new Agent();
        agent.setAgentId(resultSet.getInt(1));
        agent.setIp(resultSet.getString(2));
        agent.setPostAddress(resultSet.getString(3));
        agent.setLocation(resultSet.getString(4));
        agent.setEnabled(resultSet.getBoolean(5));
        return agent;
    }
}
