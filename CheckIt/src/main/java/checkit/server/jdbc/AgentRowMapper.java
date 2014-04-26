/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Agent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class AgentRowMapper  implements RowMapper<Agent> {
    @Override
    public Agent mapRow(ResultSet resultSet, int line) throws SQLException {
        AgentExtractor agentExtractor = new AgentExtractor();
        return agentExtractor.extractData(resultSet);
    }
}
