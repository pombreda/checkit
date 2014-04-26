/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.jdbc;

import checkit.server.domain.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class TestRowMapper implements RowMapper<Test> {

    @Override
    public Test mapRow(ResultSet resultSet, int line) throws SQLException {
        TestExtractor testExtractor = new TestExtractor();
        return testExtractor.extractData(resultSet);
    }
}
