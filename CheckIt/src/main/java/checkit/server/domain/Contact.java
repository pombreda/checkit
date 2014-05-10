/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The Contact class represents domain class and equals to one row in table "contacts" from database.
 */

package checkit.server.domain;

import java.util.List;

public class Contact {
    private int contactId;
    private String title;
    private int userId;
    private List<ContactDetail> contactDetail;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ContactDetail> getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(List<ContactDetail> contactDetail) {
        this.contactDetail = contactDetail;
    }
}
