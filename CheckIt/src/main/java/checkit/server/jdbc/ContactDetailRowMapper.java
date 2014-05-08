package checkit.server.jdbc;

import checkit.server.domain.ContactDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ContactDetailRowMapper implements RowMapper<ContactDetail> {

    @Override
    public ContactDetail mapRow(ResultSet resultSet, int line) throws SQLException {
        ContactDetailExtractor contactDetailExtractor = new ContactDetailExtractor();
        return contactDetailExtractor.extractData(resultSet);
    }
    
}
