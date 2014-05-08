package checkit.server.jdbc;

import checkit.server.domain.Check;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CheckRowMapper implements RowMapper<Check> {

    @Override
    public Check mapRow(ResultSet resultSet, int line) throws SQLException {
        CheckExtractor checkExtractor = new CheckExtractor();
        return checkExtractor.extractData(resultSet);
    }
}
