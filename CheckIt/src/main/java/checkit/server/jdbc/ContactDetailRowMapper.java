/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.ContactDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Dodo
 */
public class ContactDetailRowMapper implements RowMapper<ContactDetail> {

    @Override
    public ContactDetail mapRow(ResultSet resultSet, int line) throws SQLException {
        ContactDetailExtractor contactDetailExtractor = new ContactDetailExtractor();
        return contactDetailExtractor.extractData(resultSet);
    }
    
}
