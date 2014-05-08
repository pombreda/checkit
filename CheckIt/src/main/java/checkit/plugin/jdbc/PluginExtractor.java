package checkit.plugin.jdbc;

import checkit.plugin.domain.Plugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

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
