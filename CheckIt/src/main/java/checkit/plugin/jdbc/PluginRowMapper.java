package checkit.plugin.jdbc;

import checkit.plugin.domain.Plugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PluginRowMapper implements RowMapper<Plugin> {

    @Override
    public Plugin mapRow(ResultSet resultSet, int line) throws SQLException {
        PluginExtractor pluginExtractor = new PluginExtractor();
        return pluginExtractor.extractData(resultSet);
    }
}
