package checkit.server.jdbc;

import checkit.server.domain.Checking;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CheckingRowMapper  implements RowMapper<Checking> {

    @Override
    public Checking mapRow(ResultSet resultSet, int line) throws SQLException {
        CheckingExtractor checkingExtractor = new CheckingExtractor();
        return checkingExtractor.extractData(resultSet);
    }
    
}
