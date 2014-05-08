package checkit.server.jdbc;

import checkit.server.domain.Agent;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AgentRowMapper implements RowMapper<Agent> {
    @Override
    public Agent mapRow(ResultSet resultSet, int line) throws SQLException {
        AgentExtractor agentExtractor = new AgentExtractor();
        return agentExtractor.extractData(resultSet);
    }
}
