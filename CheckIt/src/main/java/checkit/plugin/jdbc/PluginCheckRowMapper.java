/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "plugins_check" database table row mapper
 */

package checkit.plugin.jdbc;

import checkit.plugin.domain.PluginCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PluginCheckRowMapper implements RowMapper<PluginCheck> {

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
    public PluginCheck mapRow(ResultSet resultSet, int line) throws SQLException {
        PluginCheckExtractor pluginExtractor = new PluginCheckExtractor();
        return pluginExtractor.extractData(resultSet);
    }
}
