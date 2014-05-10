/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginCheckDAO implementation
 */
package checkit.plugin.dao;

import checkit.plugin.domain.Plugin;
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

    /**
     * Get the list of all rows (check plugins) from database
     *
     * @return List of all check plugins.
     */
    @Override
    public List<Plugin> getPluginCheckList() {
        List pluginCheckList = new ArrayList();
        String sql = "SELECT * FROM plugins_check";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginCheckList = jdbcTemplate.query(sql, new PluginRowMapper());
        return pluginCheckList;    
    }

    /**
     * Get the list of all rows (check plugins) from database, which are activated
     *
     * @return List of all check plugins, which are activated.
     */
    @Override
    public List<Plugin> getActivePluginCheckList() {
        List pluginReportList = new ArrayList();
        String sql = "SELECT * FROM plugins_check WHERE enabled=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReportList = jdbcTemplate.query(sql, new PluginRowMapper());
        return pluginReportList;    
    }

    /**
     * Create new row (check plugin) in database
     *
     * @param plugin Check plugin for insertion into the database
     */
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

    /**
     * Delete row (check plugin) in database
     *
     * @param filename Filename of check plugin to delete
     */
    @Override
    public void deletePluginCheck(String filename) {
        String sql = "DELETE FROM plugins_check WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (check plugin) in database.
     * Get plugin filename and rewrite all other data.
     *
     * @param plugin Plugin to update
     */
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

    /**
     * Get row (check plugin) by plugin filename
     *
     * @param filename Filename of plugin to get
     *
     * @return Plugin or null if not exists.
     */
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
