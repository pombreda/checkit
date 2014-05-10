/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "results" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ResultExtractor implements ResultSetExtractor<Result> {
    
    /**
     * Extract appropriate database table row into the class Result
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted result from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Result extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Result result = new Result();
        result.setCheckId(resultSet.getInt(1));
        result.setAgentId(resultSet.getInt(2));
        result.setTime(resultSet.getString(3));
        result.setStatus(resultSet.getString(4));
        result.setData(resultSet.getString(5));
        return result;
    }
}
