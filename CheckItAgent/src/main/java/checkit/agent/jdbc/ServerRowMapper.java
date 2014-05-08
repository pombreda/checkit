package checkit.agent.jdbc;

import checkit.server.domain.Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ServerRowMapper implements RowMapper<Server> {

    @Override
    public Server mapRow(ResultSet resultSet, int line) throws SQLException {
        ServerExtractor serverExtractor = new ServerExtractor();
        return serverExtractor.extractData(resultSet);
    }
    
}
