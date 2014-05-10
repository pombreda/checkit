/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.jdbc.ResultRowMapper;
import checkit.server.domain.Result;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * Get the list of all rows (results) belong to check id in database, sorted descending by date
     *
     * @param checkId Id of check
     *
     * @return List of all results belong to check, sorted descending by date.
     */
    @Override
    public List<Result> getResultList(int checkId) {
        List pluginCheckList = new ArrayList();
        String sql = "SELECT * FROM results WHERE check_id=" + checkId + " ORDER BY time DESC";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginCheckList = jdbcTemplate.query(sql, new ResultRowMapper());
        return pluginCheckList;    
    }

    /**
     * Get the list of all rows (results) belong to check id in database, sorted ascending by date
     *
     * @param checkId Id of check
     *
     * @return List of all checks belong to check, sorted ascending by date.
     */
    @Override
    public List<Result> getResultListAsc(int checkId) {
        List pluginCheckList = new ArrayList();
        String sql = "SELECT * FROM results WHERE check_id=" + checkId + " ORDER BY time ASC";
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
        String sql = "INSERT INTO results (check_id, agent_id, time, status, data) VALUES (?, ?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Date timestamp = null;
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(result.getTime());
        } catch (ParseException ex) {
            
        }
        
        jdbcTemplate.update(
            sql,
            new Object[] {
                result.getCheckId(),
                result.getAgentId(),
                timestamp,
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
        String sql = "DELETE FROM results WHERE check_id=" + result.getCheckId() + " AND agent_id=" + result.getAgentId() + " AND time='" + result.getTime() + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
