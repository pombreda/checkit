package checkit.server.service;

import checkit.server.domain.Contact;
import checkit.server.domain.Reporting;
import java.util.List;

public interface ContactService {
    public List<Contact> getContactList(int userId);
    public List<Contact> getContactListByReporting(List<Reporting> reporting);
    public void createContact(Contact contact);
    public void deleteContact(int contactId);
    public void updateContact(Contact contact);
    public Contact getContactById(int contactId);
}
