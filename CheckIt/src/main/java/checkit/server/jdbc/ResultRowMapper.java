/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "results" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ResultRowMapper implements RowMapper<Result> {

    /**
     * Get result from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Result from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Result mapRow(ResultSet resultSet, int line) throws SQLException {
        ResultExtractor resultExtractor = new ResultExtractor();
        return resultExtractor.extractData(resultSet);
    }
}
