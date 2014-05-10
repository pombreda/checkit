/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "plugins_report" and "plugins_check" database table row mapper
 */

package checkit.plugin.jdbc;

import checkit.plugin.domain.Plugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PluginRowMapper implements RowMapper<Plugin> {

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
    public Plugin mapRow(ResultSet resultSet, int line) throws SQLException {
        PluginExtractor pluginExtractor = new PluginExtractor();
        return pluginExtractor.extractData(resultSet);
    }
}
