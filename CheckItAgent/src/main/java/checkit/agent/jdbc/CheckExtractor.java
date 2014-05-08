package checkit.agent.jdbc;

import checkit.server.domain.Check;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CheckExtractor implements ResultSetExtractor<Check> {
    @Override
    public Check extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Check check = new Check();
        check.setCheckId(resultSet.getInt(1));
        check.setData(resultSet.getString(2));
        check.setFilename(resultSet.getString(3));
        check.setInterval(resultSet.getInt(4));
        return check;
    }
}