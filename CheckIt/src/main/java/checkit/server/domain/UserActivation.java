/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The UserActivation class represents domain class and equals to one row in table "user_activation" from database.
 */

package checkit.server.domain;

public class UserActivation {
    private int id;
    private String hash;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
