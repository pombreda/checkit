/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "checks" database table row mapper
 */

package checkit.agent.jdbc;

import checkit.server.domain.Check;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CheckRowMapper implements RowMapper<Check> {

    /**
     * Get check from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Check from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Check mapRow(ResultSet resultSet, int line) throws SQLException {
        CheckExtractor checkExtractor = new CheckExtractor();
        return checkExtractor.extractData(resultSet);
    }
}
