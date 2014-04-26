/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Report;
import checkit.server.jdbc.ReportRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDAOImpl implements ReportDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Report> getReportListByUser(int userId) {
        List reportList = new ArrayList();
        String sql = "SELECT * FROM reports WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        reportList = jdbcTemplate.query(sql, new ReportRowMapper());
        return reportList;    
    }

    @Override
    public List<Report> getReportListByTest(int testId) {
        List reportList = new ArrayList();
        String sql = "SELECT * FROM reports WHERE test_id=" + testId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        reportList = jdbcTemplate.query(sql, new ReportRowMapper());
        return reportList;    
    }

    @Override
    public List<Report> getReportListByContact(int contactId) {
        List reportList = new ArrayList();
        String sql = "SELECT * FROM reports WHERE contact_id=" + contactId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        reportList = jdbcTemplate.query(sql, new ReportRowMapper());
        return reportList;    
    }

    @Override
    public void createReport(Report report) {
        String sql = "INSERT INTO reports (user_id, test_id, contact_id) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                report.getUserId(),
                report.getTestId(),
                report.getContactId()
            }
        );    
    }

    @Override
    public void deleteReport(Report report) {
        String sql = "DELETE FROM reports WHERE user_id=" + report.getUserId() + " AND test_id=" + report.getTestId() + " AND contact_id=" + report.getContactId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
