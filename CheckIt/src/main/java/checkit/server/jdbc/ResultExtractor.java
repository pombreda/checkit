package checkit.server.jdbc;

import checkit.server.domain.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ResultExtractor implements ResultSetExtractor<Result> {
    @Override
    public Result extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Result result = new Result();
        result.setCheckId(resultSet.getInt(1));
        result.setAgentId(resultSet.getInt(2));
        result.setTime(resultSet.getString(3));
        result.setStatus(resultSet.getString(4));
        result.setData(resultSet.getString(5));
        return result;
    }
}
