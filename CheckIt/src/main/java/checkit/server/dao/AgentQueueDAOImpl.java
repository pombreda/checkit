/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    @Override
    public List<AgentQueue> getAgentQueue() {
        List queue = new ArrayList();
        String sql = "SELECT * FROM agent_queue";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        queue = jdbcTemplate.query(sql, new AgentQueueRowMapper());
        return queue;
    }

    @Override
    public void add(AgentQueue agentQueue) {
        String sql = "INSERT INTO agent_queue (test_id, agent_id, query) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        jdbcTemplate.update(
            sql,
            new Object[] {
                agentQueue.getTestId(),
                agentQueue.getAgentId(),
                agentQueue.getQuery()
            }
        );
    }

    @Override
    public void delete(int agentQueueId) {
        String sql = "DELETE FROM agent_queue WHERE agent_queue_id=" + agentQueueId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
