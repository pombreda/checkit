/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Testing;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class TestingRowMapper  implements RowMapper<Testing> {

    @Override
    public Testing mapRow(ResultSet resultSet, int line) throws SQLException {
        TestingExtractor testingExtractor = new TestingExtractor();
        return testingExtractor.extractData(resultSet);
    }
    
}
