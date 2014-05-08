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
public class PluginReportDAOImpl implements PluginReportDAO {
    @Autowired
    private DataSource dataSource;
    
    @Override
    public List<Plugin> getPluginReportList() {
        List pluginReportList = new ArrayList();
        String sql = "SELECT * FROM plugins_report";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReportList = jdbcTemplate.query(sql, new PluginRowMapper());
        return pluginReportList;    
    }


    @Override
    public List<Plugin> getActivePluginReportList() {
        List pluginReportList = new ArrayList();
        String sql = "SELECT * FROM plugins_report WHERE enabled=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReportList = jdbcTemplate.query(sql, new PluginRowMapper());
        return pluginReportList;    
    }

    @Override
    public void createPluginReport(Plugin plugin) {
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

    @Override
    public void deletePluginReport(String filename) {
        String sql = "DELETE FROM plugins_report WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updatePluginReport(Plugin plugin) {
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

    @Override
    public Plugin getPluginReportByFilename(String filename) {
        List<Plugin> pluginReport = new ArrayList<Plugin>();
        String sql = "SELECT * FROM plugins_report WHERE filename='" + filename + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        pluginReport = jdbcTemplate.query(sql, new PluginRowMapper());
        if (pluginReport.isEmpty()) return null;
        return pluginReport.get(0);
    }
    
}
