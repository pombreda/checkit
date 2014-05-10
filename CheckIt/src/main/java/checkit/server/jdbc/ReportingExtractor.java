/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "reporting" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.Reporting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ReportingExtractor implements ResultSetExtractor<Reporting> {
    
    /**
     * Extract appropriate database table row into the class Reporting
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted reporting from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Reporting extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Reporting reporting = new Reporting();
        reporting.setUserId(resultSet.getInt(1));
        reporting.setCheckId(resultSet.getInt(2));
        reporting.setContactId(resultSet.getInt(3));
        return reporting;
    }
}
