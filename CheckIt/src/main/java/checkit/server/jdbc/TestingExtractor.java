/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Testing;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class TestingExtractor  implements ResultSetExtractor<Testing> {
    @Override
    public Testing extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Testing testing = new Testing();
        testing.setAgentId(resultSet.getInt(1));
        testing.setTestId(resultSet.getInt(2));
        testing.setUserId(resultSet.getInt(3));
        return testing;
    }
    
}
