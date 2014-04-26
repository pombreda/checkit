/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.ContactDetail;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ContactDetailService {
    public List<ContactDetail> getContactDetailList(int contactId, int userId);
    public void createContactDetail(ContactDetail contactDetail);
    public void deleteContactDetail(int contactDetailId);
    public void updateContactDetail(ContactDetail contactDetail);
    public ContactDetail getContactDetailById(int contactDetailId);
}
