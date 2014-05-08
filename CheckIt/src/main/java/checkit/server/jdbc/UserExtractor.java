package checkit.server.jdbc;

import checkit.server.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserExtractor implements ResultSetExtractor<User> {
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
