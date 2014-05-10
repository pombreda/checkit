/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "checking" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.Checking;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CheckingExtractor implements ResultSetExtractor<Checking> {
    
    /**
     * Extract appropriate database table row into the class Checking
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted checking from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Checking extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Checking checking = new Checking();
        checking.setAgentId(resultSet.getInt(1));
        checking.setCheckId(resultSet.getInt(2));
        checking.setUserId(resultSet.getInt(3));
        return checking;
    }
    
}
