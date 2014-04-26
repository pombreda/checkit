/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.UserActivation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class UserActivationExtractor implements ResultSetExtractor<UserActivation> {
    @Override
    public UserActivation extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        UserActivation userActivation = new UserActivation();
        userActivation.setHash(resultSet.getString(1));
        userActivation.setId(resultSet.getInt(2));
        userActivation.setEmail(resultSet.getString(3));
        return userActivation;
    }
}
