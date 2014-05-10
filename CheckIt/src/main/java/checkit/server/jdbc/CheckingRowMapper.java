/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "checking" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.Checking;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CheckingRowMapper  implements RowMapper<Checking> {

    /**
     * Get checking from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Checking from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Checking mapRow(ResultSet resultSet, int line) throws SQLException {
        CheckingExtractor checkingExtractor = new CheckingExtractor();
        return checkingExtractor.extractData(resultSet);
    }
    
}
