/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.domain;

import java.util.List;

/**
 *
 * @author Dodo
 */
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
