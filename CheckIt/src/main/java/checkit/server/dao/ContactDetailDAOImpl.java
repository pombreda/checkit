package checkit.server.dao;

import checkit.server.domain.ContactDetail;
import checkit.server.jdbc.ContactDetailRowMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDetailDAOImpl implements ContactDetailDAO {
    @Autowired
    private DataSource dataSource;
    
    private PGobject stringToJSON(String jsonString) {
        PGobject json = new PGobject();
        json.setType("json");
        try {
            json.setValue(jsonString);
        } catch (SQLException ex) {
            Logger.getLogger(ContactDetailDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    @Override
    public List<ContactDetail> getContactDetailList(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }

    @Override
    public List<ContactDetail> getContactDetailListWhereDownIsActive(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId + " AND down=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }

    @Override
    public List<ContactDetail> getContactDetailListWhereUpIsActive(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId + " AND up=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }
    
    @Override
    public List<ContactDetail> getContactDetailListWhereRegularIsActive(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId + " AND regular=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }
    
    @Override
    public void createContactDetail(ContactDetail contactDetail) {
        String sql = "INSERT INTO contact_detail (title, data, contact_id, user_id, filename, down, up, regular) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                contactDetail.getTitle(),
                stringToJSON(contactDetail.getData()),
                contactDetail.getContactId(),
                contactDetail.getUserId(),
                contactDetail.getFilename(),
                contactDetail.isDown(),
                contactDetail.isUp(),
                contactDetail.isRegular()
            }
        );
    }

    @Override
    public void deleteContactDetail(int contactDetailId) {
        String sql = "DELETE FROM contact_detail WHERE contact_detail_id=" + contactDetailId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updateContactDetail(ContactDetail contactDetail) {
        String sql = "UPDATE contact_detail SET title=?, data=?, down=?, up=?, regular=? WHERE contact_detail_id=? AND user_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                contactDetail.getTitle(),
                stringToJSON(contactDetail.getData()),
                contactDetail.isDown(),
                contactDetail.isUp(),
                contactDetail.isRegular(),
                contactDetail.getContactDetailId(),
                contactDetail.getUserId()
            }
        );
    }

    @Override
    public ContactDetail getContactDetailById(int contactDetailId) {
        List<ContactDetail> contactDetail = new ArrayList<ContactDetail>();
        String sql = "SELECT * FROM contact_detail WHERE contact_detail_id=" + contactDetailId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetail = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        if (contactDetail.isEmpty()) return null;
        return contactDetail.get(0);
    }

}
