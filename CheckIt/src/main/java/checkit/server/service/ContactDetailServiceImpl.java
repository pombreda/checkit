/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ContactDetailService implementation
 * All services related to contact detail
 */

package checkit.server.service;

import checkit.plugin.domain.Input;
import checkit.plugin.service.PluginReportService;
import checkit.server.dao.ContactDetailDAO;
import checkit.server.domain.Contact;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Reporting;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactDetailServiceImpl implements ContactDetailService {
    @Autowired
    private ContactDetailDAO contactDetailDAO;

    @Autowired
    private ReportingService reportingService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private PluginReportService pluginReportService;

    /**
     * Get the list of all contact details belong to user and contact
     *
     * @param contactId Id of contact
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact.
     */
    @Override
    public List<ContactDetail> getContactDetailList(int contactId, int userId) {
        return contactDetailDAO.getContactDetailList(contactId, userId);
    }
    
    /**
     * Get the list of all contact details belong to user and contact where DOWN notification is on
     *
     * @param contactId Id of contact
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact where DOWN notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListWhereDownIsActive(int contactId, int userId) {
        return contactDetailDAO.getContactDetailListWhereDownIsActive(contactId, userId);
    }

    /**
     * Get the list of all contact details belong to user and contact where UP notification is on
     *
     * @param contactId Id of contact
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact where UP notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListWhereUpIsActive(int contactId, int userId) {
        return contactDetailDAO.getContactDetailListWhereUpIsActive(contactId, userId);
    }

    /**
     * Get the list of all contact details belong to user and contact where REGULAR notification is on
     *
     * @param contactId Id of contact
     * @param userId Id of user
     *
     * @return List of all contact details belong to user and contact where REGULAR notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListWhereRegularIsActive(int contactId, int userId) {
        return contactDetailDAO.getContactDetailListWhereRegularIsActive(contactId, userId);
    }

    /**
     * Create new contact detail
     *
     * @param contactDetail Contact detail to create
     */
    @Override
    public void createContactDetail(ContactDetail contactDetail) {
        String plugin = contactDetail.getFilename();
        Object instance = pluginReportService.getPluginInstance(plugin);
        List<Input> inputs = pluginReportService.getInputs(instance);
        String dataInitJSON = pluginReportService.getInitEmptyDataJSON(inputs);
        contactDetail.setData(dataInitJSON);
        contactDetailDAO.createContactDetail(contactDetail);
    }

    /**
     * Delete contact detail
     *
     * @param contactDetailId Id of contact detail to delete
     */
    @Override
    public void deleteContactDetail(int contactDetailId) {
        contactDetailDAO.deleteContactDetail(contactDetailId);
    }

    /**
     * Update contact detail
     *
     * @param contactDetail Contact detail to update, which already includes the updated data.
     */
    @Override
    public void updateContactDetail(ContactDetail contactDetail) {
        contactDetailDAO.updateContactDetail(contactDetail);
    }

    /**
     * Get contact details by contact detail id
     *
     * @param contactDetailId Id of contact detail to get
     *
     * @return Contact detail or null if not exists.
     */
    @Override
    public ContactDetail getContactDetailById(int contactDetailId) {
        return contactDetailDAO.getContactDetailById(contactDetailId);
    }
    
    /**
     * Get contact details belong to check and where UP notification is on
     *
     * @param checkId Id of check for getting contact details
     *
     * @return List of all contact details belong to check and where UP notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListByCheckIdWhereUpIsActive(int checkId) {
        List<Reporting> reportingList = reportingService.getReportingListByCheck(checkId);
        List<Contact> contactList = contactService.getContactListByReporting(reportingList);
        List<ContactDetail> result = new ArrayList();
        for (Contact item : contactList) {
            result.addAll(getContactDetailListWhereUpIsActive(item.getContactId(), item.getUserId()));
        }
        return result;
    }

    /**
     * Get contact details belong to check and where DOWN notification is on
     *
     * @param checkId Id of check for getting contact details
     *
     * @return List of all contact details belong to check and where DOWN notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListByCheckIdWhereDownIsActive(int checkId) {
        List<Reporting> reportingList = reportingService.getReportingListByCheck(checkId);
        List<Contact> contactList = contactService.getContactListByReporting(reportingList);
        List<ContactDetail> result = new ArrayList();
        for (Contact item : contactList) {
            result.addAll(getContactDetailListWhereDownIsActive(item.getContactId(), item.getUserId()));
        }
        return result;
    }
    
    /**
     * Get contact details belong to check and where REGULAR notification is on
     *
     * @param checkId Id of check for getting contact details
     *
     * @return List of all contact details belong to check and where REGULAR notification is on.
     */
    @Override
    public List<ContactDetail> getContactDetailListByCheckIdWhereRegularIsActive(int checkId) {
        List<Reporting> reportingList = reportingService.getReportingListByCheck(checkId);
        List<Contact> contactList = contactService.getContactListByReporting(reportingList);
        List<ContactDetail> result = new ArrayList();
        for (Contact item : contactList) {
            result.addAll(getContactDetailListWhereRegularIsActive(item.getContactId(), item.getUserId()));
        }
        return result;
    }
    
}
