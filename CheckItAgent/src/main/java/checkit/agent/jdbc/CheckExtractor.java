/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "checks" database table row extractor
 */

package checkit.agent.jdbc;

import checkit.server.domain.Check;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CheckExtractor implements ResultSetExtractor<Check> {
    
    /**
     * Extract appropriate database table row into the class Check
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted check from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Check extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Check check = new Check();
        check.setCheckId(resultSet.getInt(1));
        check.setData(resultSet.getString(2));
        check.setFilename(resultSet.getString(3));
        check.setInterval(resultSet.getInt(4));
        return check;
    }
}