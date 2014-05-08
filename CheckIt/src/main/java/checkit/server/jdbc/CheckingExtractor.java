package checkit.server.jdbc;

import checkit.server.domain.Checking;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CheckingExtractor  implements ResultSetExtractor<Checking> {
    @Override
    public Checking extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Checking checking = new Checking();
        checking.setAgentId(resultSet.getInt(1));
        checking.setCheckId(resultSet.getInt(2));
        checking.setUserId(resultSet.getInt(3));
        return checking;
    }
    
}
