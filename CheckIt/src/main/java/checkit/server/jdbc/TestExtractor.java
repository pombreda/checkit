/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class TestExtractor implements ResultSetExtractor<Test> {
    @Override
    public Test extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Test test = new Test();
        test.setTestId(resultSet.getInt(1));
        test.setTitle(resultSet.getString(2));
        test.setData(resultSet.getString(3));
        test.setEnabled(resultSet.getBoolean(4));
        test.setUserId(resultSet.getInt(5));
        test.setPluginFilename(resultSet.getString(6));
        test.setInterval(resultSet.getInt(7));
        test.setOk(resultSet.getBoolean(8));
        return test;
    }
}