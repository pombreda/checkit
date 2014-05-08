package checkit.server.jdbc;

import checkit.server.domain.UserActivation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserActivationRowMapper implements RowMapper<UserActivation> {

    @Override
    public UserActivation mapRow(ResultSet resultSet, int line) throws SQLException {
        UserActivationExtractor userActivationExtractor = new UserActivationExtractor();
        return userActivationExtractor.extractData(resultSet);
    }
}
