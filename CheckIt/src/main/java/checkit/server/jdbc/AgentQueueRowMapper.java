/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "agent_queue" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.AgentQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AgentQueueRowMapper implements RowMapper<AgentQueue> {

    /**
     * Get agent queue from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Agent queue from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public AgentQueue mapRow(ResultSet resultSet, int line) throws SQLException {
        AgentQueueExtractor agentQueueExtractor = new AgentQueueExtractor();
        return agentQueueExtractor.extractData(resultSet);
    }
}
