/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.domain.Reporting;
import checkit.server.jdbc.ReportingRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportingDAOImpl implements ReportingDAO {
    @Autowired
    private DataSource dataSource;

    /**
     * Get the list of all rows (reportings) belong to user id in database
     *
     * @param userId Id of user
     *
     * @return List of all reportings belong to user.
     */
    @Override
    public List<Reporting> getReportingListByUser(int userId) {
        List reportingList = new ArrayList();
        String sql = "SELECT * FROM reporting WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        reportingList = jdbcTemplate.query(sql, new ReportingRowMapper());
        return reportingList;    
    }

    /**
     * Get the list of all rows (reportings) belong to check id in database
     *
     * @param checkId Id of check
     *
     * @return List of all reportings belong to check.
     */
    @Override
    public List<Reporting> getReportingListByCheck(int checkId) {
        List reportingList = new ArrayList();
        String sql = "SELECT * FROM reporting WHERE check_id=" + checkId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        reportingList = jdbcTemplate.query(sql, new ReportingRowMapper());
        return reportingList;    
    }

    /**
     * Get the list of all rows (reportings) belong to contact id in database
     *
     * @param contactId Id of contact
     *
     * @return List of all reportings belong to contact.
     */
    @Override
    public List<Reporting> getReportingListByContact(int contactId) {
        List reportingList = new ArrayList();
        String sql = "SELECT * FROM reporting WHERE contact_id=" + contactId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        reportingList = jdbcTemplate.query(sql, new ReportingRowMapper());
        return reportingList;    
    }

    /**
     * Create new row (reporting) in database
     *
     * @param reporting Reporting for insertion into the database
     */
    @Override
    public void createReporting(Reporting reporting) {
        String sql = "INSERT INTO reporting (user_id, check_id, contact_id) VALUES (?, ?, ?)";
                
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                reporting.getUserId(),
                reporting.getCheckId(),
                reporting.getContactId(),
            }
        );    
    }

    /**
     * Delete row (reporting) in database
     *
     * @param reporting Reporting to delete
     */
    @Override
    public void deleteReporting(Reporting reporting) {
        String sql = "DELETE FROM reporting WHERE user_id=" + reporting.getUserId() + " AND check_id=" + reporting.getCheckId() + " AND contact_id=" + reporting.getContactId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
