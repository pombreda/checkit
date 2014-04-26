/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.dao;

import checkit.server.domain.Plugin;
import checkit.plugin.jdbc.PluginRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PluginCheckDAOImpl implements PluginCheckDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Plugin> getPluginCheckList() {
        List pluginCheckList = new ArrayList();
        String sql = "SELECT * FROM plugins_check";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginCheckList = jdbcTemplate.query(sql, new PluginRowMapper());
        return pluginCheckList;    
    }

    @Override
    public List<Plugin> getActivePluginCheckList() {
        List pluginReportList = new ArrayList();
        String sql = "SELECT * FROM plugins_check WHERE enabled=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReportList = jdbcTemplate.query(sql, new PluginRowMapper());
        return pluginReportList;    
    }

    @Override
    public void createPluginCheck(Plugin plugin) {
        String sql = "INSERT INTO plugins_check (filename, enabled, title, description) VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                plugin.getFilename(),
                plugin.isEnabled(),
                plugin.getTitle(),
                plugin.getDescription()
            }
        );
    }

    @Override
    public void deletePluginCheck(String filename) {
        String sql = "DELETE FROM plugins_check WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updatePluginCheck(Plugin plugin) {
        String sql = "UPDATE plugins_check SET enabled=?, title=?, description=? WHERE filename=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                plugin.isEnabled(),
                plugin.getTitle(),
                plugin.getDescription(),
                plugin.getFilename()
            }
        );
    }

    @Override
    public Plugin getPluginCheckByFilename(String filename) {
        List<Plugin> pluginCheck = new ArrayList<Plugin>();
        String sql = "SELECT * FROM plugins_check WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginCheck = jdbcTemplate.query(sql, new PluginRowMapper());
        if (pluginCheck.isEmpty()) return null;
        return pluginCheck.get(0);
    }
    
}
