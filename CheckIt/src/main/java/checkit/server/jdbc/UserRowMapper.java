/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "users" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    /**
     * Get user from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return User from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public User mapRow(ResultSet resultSet, int line) throws SQLException {
        UserExtractor userExtractor = new UserExtractor();
        return userExtractor.extractData(resultSet);
    }

}
