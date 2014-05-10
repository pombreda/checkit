/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "users" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserExtractor implements ResultSetExtractor<User> {
    
    /**
     * Extract appropriate database table row into the class User
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted user from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        User user = new User();
        user.setUserId(resultSet.getInt(1));
        user.setUsername(resultSet.getString(2));
        user.setUserRoleId(resultSet.getInt(3));
        user.setPassword(resultSet.getString(4));
        user.setEmail(resultSet.getString(5));
        user.setEnabled(resultSet.getBoolean(6));
        user.setCreated(resultSet.getString(7));
        return user;
    }
}
