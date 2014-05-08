package checkit.server.jdbc;

import checkit.server.domain.Reporting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ReportingRowMapper implements RowMapper<Reporting> {
    
    @Override
    public Reporting mapRow(ResultSet resultSet, int line) throws SQLException {
        ReportingExtractor reportingExtractor = new ReportingExtractor();
        return reportingExtractor.extractData(resultSet);
    }
}
