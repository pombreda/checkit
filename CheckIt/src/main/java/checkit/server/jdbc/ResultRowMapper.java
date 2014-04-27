/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class ResultRowMapper implements RowMapper<Result> {

    @Override
    public Result mapRow(ResultSet resultSet, int line) throws SQLException {
        ResultExtractor resultExtractor = new ResultExtractor();
        return resultExtractor.extractData(resultSet);
    }
}
