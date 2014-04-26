/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */

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
