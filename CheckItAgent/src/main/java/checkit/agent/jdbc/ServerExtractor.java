package checkit.agent.jdbc;

import checkit.server.domain.Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ServerExtractor  implements ResultSetExtractor<Server> {
    @Override
    public Server extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Server server = new Server();
        server.setIp(resultSet.getString(1));
        server.setPostAddress(resultSet.getString(2));
        server.setPriority(resultSet.getInt(3));
        return server;
    }
}
