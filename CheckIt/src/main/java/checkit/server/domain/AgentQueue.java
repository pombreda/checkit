/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentQueue class represents domain class and equals to one row in table "agent_queue" from database.
 */

package checkit.server.domain;

public class AgentQueue {
    private int agentQueueId;
    private int checkId;
    private int agentId;
    private String query;

    public int getAgentQueueId() {
        return agentQueueId;
    }

    public void setAgentQueueId(int agentQueueId) {
        this.agentQueueId = agentQueueId;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
