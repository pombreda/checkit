/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.domain.AgentQueue;
import checkit.server.jdbc.AgentQueueRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AgentQueueDAOImpl implements AgentQueueDAO {
    @Autowired
    private DataSource dataSource;
    
    /**
     * Get the list of all rows (agentQueue) from database
     *
     * @return List of all agentQueue.
     */
    @Override
    public List<AgentQueue> getAgentQueue() {
        List queue = new ArrayList();
        String sql = "SELECT * FROM agent_queue";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        queue = jdbcTemplate.query(sql, new AgentQueueRowMapper());
        return queue;
    }

    /**
     * Create new row (agentQueue) in database
     *
     * @param agentQueue AgentQueue for insertion into the database
     */
    @Override
    public void add(AgentQueue agentQueue) {
        String sql = "INSERT INTO agent_queue (check_id, agent_id, query) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        jdbcTemplate.update(
            sql,
            new Object[] {
                agentQueue.getCheckId(),
                agentQueue.getAgentId(),
                agentQueue.getQuery()
            }
        );
    }

    /**
     * Delete row (agentQueue) in database
     *
     * @param agentQueueId Id of agentQueue to delete
     */
    @Override
    public void delete(int agentQueueId) {
        String sql = "DELETE FROM agent_queue WHERE agent_queue_id=" + agentQueueId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
