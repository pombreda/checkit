/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.plugin.service.PluginReportService;
import checkit.server.dao.ContactDetailDAO;
import checkit.server.domain.ContactDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactDetailServiceImpl implements ContactDetailService {
    @Autowired
    private ContactDetailDAO contactDetailDAO;

    @Autowired
    private PluginReportService pluginReportService;

    @Override
    public List<ContactDetail> getContactDetailList(int contactId, int userId) {
        return contactDetailDAO.getContactDetailList(contactId, userId);
    }

    @Override
    public void createContactDetail(ContactDetail contactDetail) {
        String plugin = contactDetail.getPluginFilename();
        Object instance = pluginReportService.getPluginInstance(plugin);
        List< List<String> > inputs = pluginReportService.getInputs(instance);
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
    
}
