/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.plugin.jdbc;

import checkit.server.domain.Plugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class PluginRowMapper implements RowMapper<Plugin> {

    @Override
    public Plugin mapRow(ResultSet resultSet, int line) throws SQLException {
        PluginExtractor pluginExtractor = new PluginExtractor();
        return pluginExtractor.extractData(resultSet);
    }
}
