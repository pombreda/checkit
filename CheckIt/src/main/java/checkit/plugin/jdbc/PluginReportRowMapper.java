/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "plugins_report" database table row mapper
 */

package checkit.plugin.jdbc;

import checkit.plugin.domain.PluginReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PluginReportRowMapper implements RowMapper<PluginReport> {

    /**
     * Get plugin from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Plugin from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public PluginReport mapRow(ResultSet resultSet, int line) throws SQLException {
        PluginReportExtractor pluginExtractor = new PluginReportExtractor();
        return pluginExtractor.extractData(resultSet);
    }
}
