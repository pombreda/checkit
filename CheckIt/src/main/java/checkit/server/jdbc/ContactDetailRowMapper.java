/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The "contact_detail" database table row mapper
 */

package checkit.server.jdbc;

import checkit.server.domain.ContactDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ContactDetailRowMapper implements RowMapper<ContactDetail> {

    /**
     * Get contact detail from appropriate database table row
     *
     * @param resultSet Row data given from org.springframework.jdbc.core.RowMapper
     * @param line Line number given from org.springframework.jdbc.core.RowMapper
     *
     * @return Contact detail from appropriate table row.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public ContactDetail mapRow(ResultSet resultSet, int line) throws SQLException {
        ContactDetailExtractor contactDetailExtractor = new ContactDetailExtractor();
        return contactDetailExtractor.extractData(resultSet);
    }
    
}
