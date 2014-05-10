/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The Reporting class represents domain class and equals to one row in table "reporting" from database.
 */

package checkit.server.domain;

public class Reporting {
    private int userId;
    private int checkId;
    private int contactId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
