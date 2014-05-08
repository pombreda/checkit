package checkit.server.jdbc;

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
        check.setTitle(resultSet.getString(2));
        check.setData(resultSet.getString(3));
        check.setEnabled(resultSet.getBoolean(4));
        check.setUserId(resultSet.getInt(5));
        check.setFilename(resultSet.getString(6));
        check.setInterval(resultSet.getInt(7));
        check.setOk(resultSet.getBoolean(8));
        check.setChecked(resultSet.getBoolean(9));
        return check;
    }
}