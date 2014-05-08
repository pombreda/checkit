package checkit.server.jdbc;

import checkit.server.domain.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet resultSet, int line) throws SQLException {
        ContactExtractor contactExtractor = new ContactExtractor();
        return contactExtractor.extractData(resultSet);
    }
}
