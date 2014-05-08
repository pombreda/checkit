package checkit.server.jdbc;

import checkit.server.domain.AgentQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AgentQueueRowMapper implements RowMapper<AgentQueue> {
    @Override
    public AgentQueue mapRow(ResultSet resultSet, int line) throws SQLException {
        AgentQueueExtractor agentQueueExtractor = new AgentQueueExtractor();
        return agentQueueExtractor.extractData(resultSet);
    }
}
