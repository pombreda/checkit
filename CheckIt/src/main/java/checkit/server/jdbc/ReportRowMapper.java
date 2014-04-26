/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Report;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class ReportRowMapper implements RowMapper<Report> {
    
    @Override
    public Report mapRow(ResultSet resultSet, int line) throws SQLException {
        ReportExtractor reportExtractor = new ReportExtractor();
        return reportExtractor.extractData(resultSet);
    }
}
