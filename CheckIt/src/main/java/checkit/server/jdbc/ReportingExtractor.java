package checkit.server.jdbc;

import checkit.server.domain.Reporting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ReportingExtractor implements ResultSetExtractor<Reporting> {
    @Override
    public Reporting extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Reporting reporting = new Reporting();
        reporting.setUserId(resultSet.getInt(1));
        reporting.setCheckId(resultSet.getInt(2));
        reporting.setContactId(resultSet.getInt(3));
        return reporting;
    }
}
