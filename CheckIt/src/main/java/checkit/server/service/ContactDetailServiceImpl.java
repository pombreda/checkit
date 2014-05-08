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

    @Override
    public List<ContactDetail> getContactDetailList(int contactId, int userId) {
        return contactDetailDAO.getContactDetailList(contactId, userId);
    }
    
    @Override
    public List<ContactDetail> getContactDetailListWhereDownIsActive(int contactId, int userId) {
        return contactDetailDAO.getContactDetailListWhereDownIsActive(contactId, userId);
    }

    @Override
    public List<ContactDetail> getContactDetailListWhereUpIsActive(int contactId, int userId) {
        return contactDetailDAO.getContactDetailListWhereUpIsActive(contactId, userId);
    }

    @Override
    public List<ContactDetail> getContactDetailListWhereRegularIsActive(int contactId, int userId) {
        return contactDetailDAO.getContactDetailListWhereRegularIsActive(contactId, userId);
    }

    @Override
    public void createContactDetail(ContactDetail contactDetail) {
        String plugin = contactDetail.getFilename();
        Object instance = pluginReportService.getPluginInstance(plugin);
        List<Input> inputs = pluginReportService.getInputs(instance);
        String dataInitJSON = pluginReportService.getInitEmptyDataJSON(inputs);
        contactDetail.setData(dataInitJSON);
        contactDetailDAO.createContactDetail(contactDetail);
    }

    @Override
    public void deleteContactDetail(int contactDetailId) {
        contactDetailDAO.deleteContactDetail(contactDetailId);
    }

    @Override
    public void updateContactDetail(ContactDetail contactDetail) {
        contactDetailDAO.updateContactDetail(contactDetail);
    }

    @Override
    public ContactDetail getContactDetailById(int contactDetailId) {
        return contactDetailDAO.getContactDetailById(contactDetailId);
    }
    
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
