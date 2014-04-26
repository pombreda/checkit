/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.dao.ContactDAO;
import checkit.server.domain.Contact;
import checkit.server.domain.Report;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDAO contactDAO;

    @Override
    public List<Contact> getContactList(int userId) {
        return contactDAO.getContactList(userId);
    }

    @Override
    public List<Contact> getContactListByReport(List<Report> report) {
        List<Contact> result = new ArrayList();
        for (Report item : report) {
            result.add(getContactById(item.getContactId()));
        }
        return result;
    }
    
    @Override
    public void createContact(Contact contact) {
        contactDAO.createContact(contact);
    }

    @Override
    public void deleteContact(int contactId) {
        contactDAO.deleteContact(contactId);
    }

    @Override
    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

    @Override
    public Contact getContactById(int contactId) {
        return contactDAO.getContactById(contactId);
    }

}
