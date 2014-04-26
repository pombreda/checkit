/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Agent;
import checkit.server.domain.AgentQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class AgentQueueRowMapper implements RowMapper<AgentQueue> {
    @Override
    public AgentQueue mapRow(ResultSet resultSet, int line) throws SQLException {
        AgentQueueExtractor agentQueueExtractor = new AgentQueueExtractor();
        return agentQueueExtractor.extractData(resultSet);
    }
}
