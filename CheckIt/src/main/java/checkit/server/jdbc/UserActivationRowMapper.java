/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.UserActivation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class UserActivationRowMapper implements RowMapper<UserActivation> {

    @Override
    public UserActivation mapRow(ResultSet resultSet, int line) throws SQLException {
        UserActivationExtractor userActivationExtractor = new UserActivationExtractor();
        return userActivationExtractor.extractData(resultSet);
    }
}
