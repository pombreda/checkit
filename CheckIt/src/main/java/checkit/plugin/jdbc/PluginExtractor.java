/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.jdbc;

import checkit.server.domain.Plugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class PluginExtractor implements ResultSetExtractor<Plugin> {
    @Override
    public Plugin extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Plugin plugin = new Plugin();
        plugin.setFilename(resultSet.getString(1));
        plugin.setEnabled(resultSet.getBoolean(2));
        plugin.setTitle(resultSet.getString(3));
        plugin.setDescription(resultSet.getString(4));
        return plugin;
    }
}
