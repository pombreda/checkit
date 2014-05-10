/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "user_activation" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.UserActivation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserActivationRowMapper implements RowMapper<UserActivation> {

    /**
     * Get user activation from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return User activation from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public UserActivation mapRow(ResultSet resultSet, int line) throws SQLException {
        UserActivationExtractor userActivationExtractor = new UserActivationExtractor();
        return userActivationExtractor.extractData(resultSet);
    }
}
