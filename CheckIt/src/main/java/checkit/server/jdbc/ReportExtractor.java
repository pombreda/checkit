/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Report;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class ReportExtractor implements ResultSetExtractor<Report> {
    @Override
    public Report extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Report report = new Report();
        report.setUserId(resultSet.getInt(1));
        report.setTestId(resultSet.getInt(2));
        report.setContactId(resultSet.getInt(3));
        return report;
    }
}
