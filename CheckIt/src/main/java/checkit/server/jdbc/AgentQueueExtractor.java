/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "agent_queue" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.AgentQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class AgentQueueExtractor implements ResultSetExtractor<AgentQueue> {
    
    /**
     * Extract appropriate database table row into the class AgentQueue
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted agent queue from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public AgentQueue extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        AgentQueue agentQueue = new AgentQueue();
        agentQueue.setAgentQueueId(resultSet.getInt(1));
        agentQueue.setAgentId(resultSet.getInt(2));
        agentQueue.setCheckId(resultSet.getInt(3));
        agentQueue.setQuery(resultSet.getString(4));
        return agentQueue;
    }
}
