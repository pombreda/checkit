/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "contacts" database table row extractor
 */

package checkit.server.jdbc;

import checkit.server.domain.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ContactExtractor implements ResultSetExtractor<Contact> {
    
    /**
     * Extract appropriate database table row into the class Contact
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     *
     * @return Extracted contact from appropriate table row.
     * 
     * @throws java.sql.SQLException
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public Contact extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Contact contact = new Contact();
        contact.setContactId(resultSet.getInt(1));
        contact.setTitle(resultSet.getString(2));
        contact.setUserId(resultSet.getInt(3));
        return contact;
    }    
}
