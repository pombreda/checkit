/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "agents" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.Agent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AgentRowMapper implements RowMapper<Agent> {

    /**
     * Get agent from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Agent from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Agent mapRow(ResultSet resultSet, int line) throws SQLException {
        AgentExtractor agentExtractor = new AgentExtractor();
        return agentExtractor.extractData(resultSet);
    }
}
