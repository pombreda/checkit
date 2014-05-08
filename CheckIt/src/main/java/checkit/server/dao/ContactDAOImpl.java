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

    @Override
    public List<Contact> getContactList(int userId) {
        List contactList = new ArrayList();
        String sql = "SELECT * FROM contacts WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactList = jdbcTemplate.query(sql, new ContactRowMapper());
        return contactList;
    }

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

    @Override
    public void deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE contact_id=" + contactId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

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
