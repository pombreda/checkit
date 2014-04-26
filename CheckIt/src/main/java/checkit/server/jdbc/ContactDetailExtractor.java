/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.jdbc;

import checkit.server.domain.ContactDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Dodo
 */
public class ContactDetailExtractor implements ResultSetExtractor<ContactDetail> {
    @Override
    public ContactDetail extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setContactDetailId(resultSet.getInt(1));
        contactDetail.setTitle(resultSet.getString(2));
        contactDetail.setData(resultSet.getString(3));
        contactDetail.setContactId(resultSet.getInt(4));
        contactDetail.setUserId(resultSet.getInt(5));
        contactDetail.setPluginFilename(resultSet.getString(6));
        contactDetail.setDown(resultSet.getBoolean(7));
        contactDetail.setUp(resultSet.getBoolean(8));
        contactDetail.setRegular(resultSet.getBoolean(9));
        return contactDetail;
    }
}
