/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.Contact;
import checkit.server.domain.Report;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ContactService {
    public List<Contact> getContactList(int userId);
    public List<Contact> getContactListByReport(List<Report> report);
    public void createContact(Contact contact);
    public void deleteContact(int contactId);
    public void updateContact(Contact contact);
    public Contact getContactById(int contactId);
}
