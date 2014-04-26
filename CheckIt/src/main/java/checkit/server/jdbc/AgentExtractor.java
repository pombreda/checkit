/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Agent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class AgentExtractor  implements ResultSetExtractor<Agent> {
    @Override
    public Agent extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Agent agent = new Agent();
        agent.setAgentId(resultSet.getInt(1));
        agent.setIp(resultSet.getString(2));
        agent.setLocation(resultSet.getString(3));
        agent.setEnabled(resultSet.getBoolean(4));
        return agent;
    }
}
