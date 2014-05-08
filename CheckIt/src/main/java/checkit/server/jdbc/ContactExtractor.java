package checkit.server.jdbc;

import checkit.server.domain.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ContactExtractor implements ResultSetExtractor<Contact> {
    @Override
    public Contact extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Contact contact = new Contact();
        contact.setContactId(resultSet.getInt(1));
        contact.setTitle(resultSet.getString(2));
        contact.setUserId(resultSet.getInt(3));
        return contact;
    }    
}
