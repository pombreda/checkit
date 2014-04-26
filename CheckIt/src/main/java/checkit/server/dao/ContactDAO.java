/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Contact;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ContactDAO {
    public List<Contact> getContactList(int userId);
    public void createContact(Contact contact);
    public void deleteContact(int contactId);
    public void updateContact(Contact contact);
    public Contact getContactById(int contactId);
}
