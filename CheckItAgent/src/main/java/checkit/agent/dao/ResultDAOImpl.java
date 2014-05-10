/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ResultDAO implementation
 */

package checkit.agent.dao;

import checkit.agent.jdbc.ResultRowMapper;
import checkit.server.domain.Result;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResultDAOImpl implements ResultDAO {
    @Autowired
    private DataSource dataSource;
    
    /**
     * Get current timestamp
     *
     * @return Current timestamp.
     */
    private static Timestamp getCurrentTimestamp() {
	Date today = new java.util.Date();
	return new Timestamp(today.getTime());
    }
        
    /**
     * Convert JSON string to Postgres JSON
     *
     * @param jsonString JSON string to convert
     *
     * @return Postgres JSON.
     */
    private PGobject stringToJSON(String jsonString) {
        PGobject json = new PGobject();
        json.setType("json");
        try {
            json.setValue(jsonString);
        } catch (SQLException ex) {
            Logger.getLogger(ResultDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    /**
     * Get the list of all rows (results) in database sorted ascending by date
     *
     * @return List of all results sorted ascending by date.
     */
    @Override
    public List<Result> getResultList() {
        List pluginCheckList = new ArrayList();
        String sql = "SELECT * FROM results ORDER BY time ASC";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginCheckList = jdbcTemplate.query(sql, new ResultRowMapper());
        return pluginCheckList;    
    }

    /**
     * Create new row (result) in database
     *
     * @param result Result for insertion into the database
     */
    @Override
    public void createResult(Result result) {
        String sql = "INSERT INTO results (check_id, time, status, data) VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                result.getCheckId(),
                getCurrentTimestamp(),
                result.getStatus(),
                stringToJSON(result.getData())
            }
        );
    }

    /**
     * Delete row (result) in database
     *
     * @param result Result to delete
     */
    @Override
    public void deleteResult(Result result) {
        String sql = "DELETE FROM results WHERE check_id=" + result.getCheckId() + " AND time='" + result.getTime() + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
