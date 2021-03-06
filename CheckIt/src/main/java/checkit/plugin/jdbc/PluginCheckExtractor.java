/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "plugins_check" database table row extractor
 */

package checkit.plugin.jdbc;

import checkit.plugin.domain.PluginCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class PluginCheckExtractor implements ResultSetExtractor<PluginCheck> {
    
    /**
     * Extract appropriate database table row into the class Plugin
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted plugin from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public PluginCheck extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        PluginCheck plugin = new PluginCheck();
        plugin.setFilename(resultSet.getString(1));
        plugin.setEnabled(resultSet.getBoolean(2));
        plugin.setTitle(resultSet.getString(3));
        plugin.setDescription(resultSet.getString(4));
        return plugin;
    }
}
