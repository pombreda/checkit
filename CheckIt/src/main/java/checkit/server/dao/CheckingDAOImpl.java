/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.domain.Checking;
import checkit.server.jdbc.CheckingRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CheckingDAOImpl implements CheckingDAO {
    @Autowired
    private DataSource dataSource;

    /**
     * Create new row (checking) in database
     *
     * @param checking Checking for insertion into the database
     */
    @Override
    public void createChecking(Checking checking) {
        String sql = "INSERT INTO checking (agent_id, check_id, user_id) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                checking.getAgentId(),
                checking.getCheckId(),
                checking.getUserId()
            }
        );
    }
    
    /**
     * Get the list of all rows (checkings) belong to user id in database
     *
     * @param userId Id of user
     *
     * @return List of all checkings belong to user.
     */
    @Override
    public List<Checking> getCheckingByUserId(int userId) {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    /**
     * Get the list of all rows (checkings) belong to agent id in database
     *
     * @param agentId Id of agent
     *
     * @return List of all checkings belong to agent.
     */
    @Override
    public List<Checking> getCheckingByAgentId(int agentId) {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking WHERE agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    /**
     * Get the list of all rows (checkings) belong to check id in database
     *
     * @param checkId Id of check
     *
     * @return List of all checkings belong to check.
     */
    @Override
    public List<Checking> getCheckingByCheckId(int checkId) {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking WHERE check_id=" + checkId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    /**
     * Get the list of all rows (checkings) belong to user and agent id in database
     *
     * @param checkId Id of check
     * @param agentId Id of agent
     *
     * @return List of all checkings belong to user and check.
     */
    @Override
    public Checking getCheckingByCheckAndAgentId(int checkId, int agentId) {
        List<Checking> checkingList = new ArrayList<Checking>();
        String sql = "SELECT * FROM checking WHERE check_id=" + checkId + " AND agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        if (checkingList.isEmpty()) return null;
        return checkingList.get(0); 
    }

    /**
     * Get the list of all rows (checkings) from database
     *
     * @return List of all checkings.
     */
    @Override
    public List<Checking> getAllChecking() {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    /**
     * Delete row (checking) in database
     *
     * @param checking Checking to delete
     */
    @Override
    public void deleteChecking(Checking checking) {
        String sql = "DELETE FROM checking WHERE check_id=" + checking.getCheckId() + " AND agent_id=" + checking.getAgentId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update agent of checking in database.
     *
     * @param checking Checking to update its agent
     * @param agentId New agent id to set
     */
    @Override
    public void updateAgentOfChecking(Checking checking, int agentId) {
        String sql = "UPDATE checking SET agent_id=? WHERE check_id=? AND agent_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                agentId,
                checking.getCheckId(),
                checking.getAgentId()
            }
        );    
    }

}
