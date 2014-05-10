/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ContactDetailDAO interface
 */

package checkit.server.dao;

import checkit.server.domain.ContactDetail;
import java.util.List;

public interface ContactDetailDAO {
    public List<ContactDetail> getContactDetailList(int contactId, int userId);
    public List<ContactDetail> getContactDetailListWhereDownIsActive(int contactId, int userId);
    public List<ContactDetail> getContactDetailListWhereUpIsActive(int contactId, int userId);
    public List<ContactDetail> getContactDetailListWhereRegularIsActive(int contactId, int userId);
    public void createContactDetail(ContactDetail contactDetail);
    public void deleteContactDetail(int contactDetailId);
    public void updateContactDetail(ContactDetail contactDetail);
    public ContactDetail getContactDetailById(int contactDetailId);
}
