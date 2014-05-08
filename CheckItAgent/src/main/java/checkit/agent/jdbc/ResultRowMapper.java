package checkit.agent.jdbc;

import checkit.server.domain.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ResultRowMapper implements RowMapper<Result> {

    @Override
    public Result mapRow(ResultSet resultSet, int line) throws SQLException {
        ResultExtractor resultExtractor = new ResultExtractor();
        return resultExtractor.extractData(resultSet);
    }
}
