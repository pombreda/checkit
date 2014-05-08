package checkit.server.dao;

import checkit.server.domain.Contact;
import java.util.List;

public interface ContactDAO {
    public List<Contact> getContactList(int userId);
    public void createContact(Contact contact);
    public void deleteContact(int contactId);
    public void updateContact(Contact contact);
    public Contact getContactById(int contactId);
}
