/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.dao;

import checkit.agent.jdbc.ServerRowMapper;
import checkit.server.domain.Server;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServerDAOImpl implements ServerDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Server> getServerList() {
        List serverList = new ArrayList();
        String sql = "SELECT * FROM servers";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        serverList = jdbcTemplate.query(sql, new ServerRowMapper());
        return serverList;        
    }
    
    @Override
    public Server getServerWithTheHighestPriority() {
        List<Server> server = new ArrayList<Server>();
        String sql = "SELECT * FROM servers WHERE priority=(SELECT MAX(priority) FROM servers)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        server = jdbcTemplate.query(sql, new ServerRowMapper());
        if (server.isEmpty()) return null;
        return server.get(0);
    }

    @Override
    public Server getServerByIp(String ip) {
        List<Server> server = new ArrayList<Server>();
        String sql = "SELECT * FROM servers WHERE ip='" + ip + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        server = jdbcTemplate.query(sql, new ServerRowMapper());
        if (server.isEmpty()) return null;
        return server.get(0);
    }
    
}
