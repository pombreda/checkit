/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.domain.Agent;
import checkit.server.jdbc.AgentRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AgentDAOImpl implements AgentDAO {
    @Autowired
    private DataSource dataSource;

    /**
     * Get the list of all rows (agents) from database
     *
     * @return List of all agents.
     */
    @Override
    public List<Agent> getAgentList() {
        List agentList = new ArrayList();
        String sql = "SELECT * FROM agents";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        agentList = jdbcTemplate.query(sql, new AgentRowMapper());
        return agentList;
    }

    /**
     * Create new row (agent) in database
     *
     * @param agent Agent for insertion into the database
     */
    @Override
    public void createAgent(Agent agent) {
        String sql = "INSERT INTO agents (ip, post_address, location, enabled) VALUES (?, ?, ?, FALSE)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                agent.getIp(),
                agent.getPostAddress(),
                agent.getLocation()
            }
        );
    }

    /**
     * Delete row (agent) in database
     *
     * @param agentId Id of agent to delete
     */
    @Override
    public void deleteAgent(int agentId) {
        String sql = "DELETE FROM agents WHERE agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (agent) in database.
     * Get agent id and rewrite all other data.
     *
     * @param agent Agent to update
     */
    @Override
    public void updateAgent(Agent agent) {
        String sql = "UPDATE agents SET ip=?, post_address=?, location=?, enabled=? WHERE agent_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                agent.getIp(),
                agent.getPostAddress(),
                agent.getLocation(),
                agent.isEnabled(),
                agent.getAgentId()
            }
        );
    }

    /**
     * Get row (agent) by agent id
     *
     * @param agentId Id of agent to get
     *
     * @return Agent or null if not exists.
     */
    @Override
    public Agent getAgentById(int agentId) {
        List<Agent> agent = new ArrayList<Agent>();
        String sql = "SELECT * FROM agents WHERE agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        agent = jdbcTemplate.query(sql, new AgentRowMapper());
        if (agent.isEmpty()) return null;
        return agent.get(0);  
    }

    /**
     * Get row (agent) by agent ip
     *
     * @param ip Ip of agent to get
     *
     * @return Agent or null if not exists.
     */
    @Override
    public Agent getAgentByIp(String ip) {
        List<Agent> agent = new ArrayList<Agent>();
        String sql = "SELECT * FROM agents WHERE ip='" + ip + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        agent = jdbcTemplate.query(sql, new AgentRowMapper());
        if (agent.isEmpty()) return null;
        return agent.get(0);  
    }
    
}
