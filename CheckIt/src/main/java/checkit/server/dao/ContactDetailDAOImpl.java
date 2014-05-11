/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.global.component.JsonComponent;
import checkit.server.domain.ContactDetail;
import checkit.server.jdbc.ContactDetailRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDetailDAOImpl implements ContactDetailDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JsonComponent jsonService;
    
    /**
     * Get the list of all rows (contactDetails) belong to user and contact id in database
     *
     * @param contactId Id of contect
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact.
     */
    @Override
    public List<ContactDetail> getContactDetailList(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }

    /**
     * Get the list of all rows (contactDetails) belong to user and contact id in database, where DOWN notification is on
     *
     * @param contactId Id of contect
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact, where DOWN notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListWhereDownIsActive(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId + " AND down=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }

    /**
     * Get the list of all rows (contactDetails) belong to user and contact id in database, where UP notification is on
     *
     * @param contactId Id of contect
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact, where UP notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListWhereUpIsActive(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId + " AND up=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }
    
    /**
     * Get the list of all rows (contactDetails) belong to user and contact id in database, where REGULAR notification is on
     *
     * @param contactId Id of contect
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact, where REGULAR notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListWhereRegularIsActive(int contactId, int userId) {
        List contactDetailList = new ArrayList();
        String sql = "SELECT * FROM contact_detail WHERE contact_id=" + contactId + " AND user_id=" + userId + " AND regular=true";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        contactDetailList = jdbcTemplate.query(sql, new ContactDetailRowMapper());
        return contactDetailList;
    }
    
    /**
     * Create new row (contactDetail) in database
     *
     * @param contactDetail Contact detail for insertion into the database
     */
    @Override
    public void createContactDetail(ContactDetail contactDetail) {
        String sql = "INSERT INTO contact_detail (title, data, contact_id, user_id, filename, down, up, regular) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                contactDetail.getTitle(),
                jsonService.stringToJSON(contactDetail.getData()),
                contactDetail.getContactId(),
                contactDetail.getUserId(),
                contactDetail.getFilename(),
                contactDetail.isDown(),
                contactDetail.isUp(),
                contactDetail.isRegular()
            }
        );
    }

    /**
     * Delete row (contactDetail) in database
     *
     * @param contactDetailId Id of contact detail to delete
     */
    @Override
    public void deleteContactDetail(int contactDetailId) {
        String sql = "DELETE FROM contact_detail WHERE contact_detail_id=" + contactDetailId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (contactDetail) in database.
     * Get contact detail id and user id and rewrite all other data.
     *
     * @param contactDetail Contact detail to update
     */
    @Override
    public void updateContactDetail(ContactDetail contactDetail) {
        String sql = "UPDATE contact_detail SET title=?, data=?, down=?, up=?, regular=? WHERE contact_detail_id=? AND user_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                contactDetail.getTitle(),
                jsonService.stringToJSON(contactDetail.getData()),
                contactDetail.isDown(),
                contactDetail.isUp(),
                contactDetail.isRegular(),
                contactDetail.getContactDetailId(),
                contactDetail.getUserId()
            }
        );
    }

    /**
     * Get row (contactDetail) by agent id
     *
     * @param contactDetailId Id of agent to get
     *
     * @return Contact detail or null if not exists.
     */
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
