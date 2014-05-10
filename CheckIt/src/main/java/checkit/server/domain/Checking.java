/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The Checking class represents domain class and equals to one row in table "checking" from database.
 */

package checkit.server.domain;

public class Checking {
    private int agentId;
    private int checkId;
    private int userId;

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
