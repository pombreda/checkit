/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "reporting" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.Reporting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ReportingRowMapper implements RowMapper<Reporting> {

    /**
     * Get reporting from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Reporting from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Reporting mapRow(ResultSet resultSet, int line) throws SQLException {
        ReportingExtractor reportingExtractor = new ReportingExtractor();
        return reportingExtractor.extractData(resultSet);
    }
}
