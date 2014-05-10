/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ContactService implementation
 * All services related to contact
 */

package checkit.server.service;

import checkit.server.dao.ContactDAO;
import checkit.server.domain.Contact;
import checkit.server.domain.Reporting;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDAO contactDAO;

    /**
     * Get the list of all contacts belong to user
     *
     * @param userId Id of user
     *
     * @return List of all contact details belong to user.
     */
    @Override
    public List<Contact> getContactList(int userId) {
        return contactDAO.getContactList(userId);
    }

    /**
     * Get the list of all contacts contained in reportings
     *
     * @param reporting List of reportings
     *
     * @return List of all contacts contained in reportings.
     */
    @Override
    public List<Contact> getContactListByReporting(List<Reporting> reporting) {
        List<Contact> result = new ArrayList();
        for (Reporting item : reporting) {
            result.add(getContactById(item.getContactId()));
        }
        return result;
    }
    
    /**
     * Create new contact
     *
     * @param contact Contact to create
     */
    @Override
    public void createContact(Contact contact) {
        contactDAO.createContact(contact);
    }

    /**
     * Delete contact
     *
     * @param contactId Id of contact to delete
     */
    @Override
    public void deleteContact(int contactId) {
        contactDAO.deleteContact(contactId);
    }

    /**
     * Update contact
     *
     * @param contact Contact to update, which already includes the updated data.
     */
    @Override
    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

    /**
     * Get contact by contact id
     *
     * @param contactId Id of contact to get
     *
     * @return Contact or null if not exists.
     */
    @Override
    public Contact getContactById(int contactId) {
        return contactDAO.getContactById(contactId);
    }

}
