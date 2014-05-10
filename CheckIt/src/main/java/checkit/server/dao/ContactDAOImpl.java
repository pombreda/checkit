/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.domain.Contact;
import checkit.server.jdbc.ContactRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDAOImpl implements ContactDAO {
    @Autowired
    private DataSource dataSource;

    /**
     * Get the list of all rows (contacts) belong to user id in database
     *
     * @param userId Id of user
     *
     * @return List of all contacts belong to user.
     */
    @Override
    public List<Contact> getContactList(int userId) {
        List contactList = new ArrayList();
        String sql = "SELECT * FROM contacts WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactList = jdbcTemplate.query(sql, new ContactRowMapper());
        return contactList;
    }

    /**
     * Create new row (contact) in database
     *
     * @param contact Contact for insertion into the database
     */
    @Override
    public void createContact(Contact contact) {
        String sql = "INSERT INTO contacts (title, user_id) VALUES (?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                contact.getTitle(),
                contact.getUserId()
            }
        );
    }

    /**
     * Delete row (contact) in database
     *
     * @param contactId Id of contact to delete
     */
    @Override
    public void deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE contact_id=" + contactId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (contact) in database.
     * Get contact id and user id and rewrite all other data.
     *
     * @param contact Contact to update
     */
    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET title=? WHERE contact_id=? AND user_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                contact.getTitle(),
                contact.getContactId(),
                contact.getUserId()
            }
        );
    }

    /**
     * Get row (contact) by contact id
     *
     * @param contactId Id of contact to get
     *
     * @return Contact or null if not exists.
     */
    @Override
    public Contact getContactById(int contactId) {
        List<Contact> contact = new ArrayList<Contact>();
        String sql = "SELECT * FROM contacts WHERE contact_id=" + contactId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contact = jdbcTemplate.query(sql, new ContactRowMapper());
        if (contact.isEmpty()) return null;
        return contact.get(0);
    }

}
