/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Testing;
import checkit.server.jdbc.TestingRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestingDAOImpl implements TestingDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public void createTesting(Testing testing) {
        String sql = "INSERT INTO testing (agent_id, test_id, user_id) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                testing.getAgentId(),
                testing.getTestId(),
                testing.getUserId()
            }
        );
    }
    
    @Override
    public List<Testing> getTestingByUserId(int userId) {
        List testingList = new ArrayList();
        String sql = "SELECT * FROM testing WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        testingList = jdbcTemplate.query(sql, new TestingRowMapper());
        return testingList;    
    }

    @Override
    public List<Testing> getTestingByAgentId(int agentId) {
        List testingList = new ArrayList();
        String sql = "SELECT * FROM testing WHERE agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        testingList = jdbcTemplate.query(sql, new TestingRowMapper());
        return testingList;    
    }

    @Override
    public List<Testing> getTestingByTestId(int testId) {
        List testingList = new ArrayList();
        String sql = "SELECT * FROM testing WHERE test_id=" + testId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        testingList = jdbcTemplate.query(sql, new TestingRowMapper());
        return testingList;    
    }

    @Override
    public List<Testing> getTestingByTestAndAgentId(int testId, int agentId) {
        List testingList = new ArrayList();
        String sql = "SELECT * FROM testing WHERE test_id=" + testId + " AND agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        testingList = jdbcTemplate.query(sql, new TestingRowMapper());
        return testingList;    
    }

    @Override
    public List<Testing> getAllTesting() {
        List testingList = new ArrayList();
        String sql = "SELECT * FROM testing";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        testingList = jdbcTemplate.query(sql, new TestingRowMapper());
        return testingList;    
    }

    @Override
    public void deleteTesting(Testing testing) {
        String sql = "DELETE FROM testing WHERE test_id=" + testing.getTestId() + " AND agent_id=" + testing.getAgentId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updateAgentOfTesting(Testing testing, int agentId) {
        String sql = "UPDATE testing SET agent_id=? WHERE test_id=? AND agent_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                agentId,
                testing.getTestId(),
                testing.getAgentId()
            }
        );    
    }

}
