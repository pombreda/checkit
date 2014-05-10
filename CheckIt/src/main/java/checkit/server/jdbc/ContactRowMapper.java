/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "contacts" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ContactRowMapper implements RowMapper<Contact> {

    /**
     * Get contact from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Contact from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public Contact mapRow(ResultSet resultSet, int line) throws SQLException {
        ContactExtractor contactExtractor = new ContactExtractor();
        return contactExtractor.extractData(resultSet);
    }
}
