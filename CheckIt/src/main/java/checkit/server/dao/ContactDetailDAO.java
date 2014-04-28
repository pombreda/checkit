/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.ContactDetail;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ContactDetailDAO {
    public List<ContactDetail> getContactDetailList(int contactId, int userId);
    public List<ContactDetail> getContactDetailListWhereDownIsActive(int contactId, int userId);
    public List<ContactDetail> getContactDetailListWhereUpIsActive(int contactId, int userId);
    public void createContactDetail(ContactDetail contactDetail);
    public void deleteContactDetail(int contactDetailId);
    public void updateContactDetail(ContactDetail contactDetail);
    public ContactDetail getContactDetailById(int contactDetailId);
}
