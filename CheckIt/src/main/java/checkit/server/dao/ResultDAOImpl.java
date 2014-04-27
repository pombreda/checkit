/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    @Override
    public List<Result> getResultList(int testId) {
        List pluginCheckList = new ArrayList();
        String sql = "SELECT * FROM results WHERE test_id=" + testId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginCheckList = jdbcTemplate.query(sql, new ResultRowMapper());
        return pluginCheckList;    
    }

    @Override
    public void createResult(Result result) {
        String sql = "INSERT INTO results (test_id, agent_id, time, ok, data) VALUES (?, ?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        Date timestamp = null;
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(result.getTime());
        } catch (ParseException ex) {
            
        }
        
        jdbcTemplate.update(
            sql,
            new Object[] {
                result.getTestId(),
                result.getAgentId(),
                timestamp,
                result.isOk(),
                stringToJSON(result.getData())
            }
        );
    }

    @Override
    public void deleteResult(Result result) {
        String sql = "DELETE FROM results WHERE test_id=" + result.getTestId() + " AND agent_id=" + result.getAgentId() + " AND time='" + result.getTime() + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
    
}
