/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Test;
import checkit.server.jdbc.TestRowMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDAOImpl implements TestDAO {
    @Autowired
    private DataSource dataSource;

    private PGobject stringToJSON(String jsonString) {
        PGobject json = new PGobject();
        json.setType("json");
        try {
            json.setValue(jsonString);
        } catch (SQLException ex) {
            Logger.getLogger(TestDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    @Override
    public List<Test> getTestList(int userId) {
        List testList = new ArrayList();
        String sql = "SELECT * FROM tests WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        testList = jdbcTemplate.query(sql, new TestRowMapper());
        return testList;    
    }

    @Override
    public void createTest(Test test) {
        String sql = "INSERT INTO tests (title, data, enabled, user_id, plugin_filename, interval) VALUES (?, ?, ?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                test.getTitle(),
                stringToJSON(test.getData()),
                test.isEnabled(),
                test.getUserId(),
                test.getPluginFilename(),
                test.getInterval()
            }
        );
    }

    @Override
    public void deleteTest(int testId) {
        String sql = "DELETE FROM tests WHERE test_id=" + testId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updateTest(Test test) {
        String sql = "UPDATE tests SET title=?, data=?, enabled=?, interval=? WHERE test_id=? AND user_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                test.getTitle(),
                stringToJSON(test.getData()),
                test.isEnabled(),
                test.getInterval(),
                test.getTestId(),
                test.getUserId()
            }
        );
    }

    @Override
    public Test getTestById(int testId) {
        List<Test> test = new ArrayList<Test>();
        String sql = "SELECT * FROM tests WHERE test_id=" + testId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        test = jdbcTemplate.query(sql, new TestRowMapper());
        if (test.isEmpty()) return null;
        return test.get(0);
    }

}
