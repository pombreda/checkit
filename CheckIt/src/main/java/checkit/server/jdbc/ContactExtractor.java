/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
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
