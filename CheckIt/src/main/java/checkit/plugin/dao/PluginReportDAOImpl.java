/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The PluginReportDAO implementation
 */
package checkit.plugin.dao;

import checkit.plugin.domain.PluginReport;
import checkit.plugin.jdbc.PluginReportRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PluginReportDAOImpl implements PluginReportDAO {
    @Autowired
    private DataSource dataSource;
    
    /**
     * Get the list of all rows (report plugins) from database
     *
     * @return List of all report plugins.
     */
    @Override
    public List<PluginReport> getPluginReportList() {
        List pluginReportList = new ArrayList();
        String sql = "SELECT * FROM plugins_report";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReportList = jdbcTemplate.query(sql, new PluginReportRowMapper());
        return pluginReportList;    
    }


    /**
     * Get the list of all rows (report plugins) from database, which are activated
     *
     * @return List of all report plugins, which are activated.
     */
    @Override
    public List<PluginReport> getActivePluginReportList() {
        List pluginReportList = new ArrayList();
        String sql = "SELECT * FROM plugins_report WHERE enabled=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReportList = jdbcTemplate.query(sql, new PluginReportRowMapper());
        return pluginReportList;    
    }

    /**
     * Create new row (report plugin) in database
     *
     * @param plugin Report plugin for insertion into the database
     */
    @Override
    public void createPluginReport(PluginReport plugin) {
        String sql = "INSERT INTO plugins_report (filename, enabled, title, description) VALUES (?, ?, ?, ?)";
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
     * Delete row (report plugin) in database
     *
     * @param filename Filename of report plugin to delete
     */
    @Override
    public void deletePluginReport(String filename) {
        String sql = "DELETE FROM plugins_report WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (report plugin) in database.
     * Get plugin filename and rewrite all other data.
     *
     * @param plugin Plugin to update
     */
    @Override
    public void updatePluginReport(PluginReport plugin) {
        String sql = "UPDATE plugins_report SET enabled=?, title=?, description=? WHERE filename=?";
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
     * Get row (report plugin) by plugin filename
     *
     * @param filename Filename of plugin to get
     *
     * @return Plugin or null if not exists.
     */
    @Override
    public PluginReport getPluginReportByFilename(String filename) {
        List<PluginReport> pluginReport = new ArrayList<PluginReport>();
        String sql = "SELECT * FROM plugins_report WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReport = jdbcTemplate.query(sql, new PluginReportRowMapper());
        if (pluginReport.isEmpty()) return null;
        return pluginReport.get(0);
    }
    
}
