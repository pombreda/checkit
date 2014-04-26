/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.domain;

/**
 *
 * @author Dodo
 */
public class AgentQueue {
    private int agentQueueId;
    private int testId;
    private int agentId;
    private String query;

    public int getAgentQueueId() {
        return agentQueueId;
    }

    public void setAgentQueueId(int agentQueueId) {
        this.agentQueueId = agentQueueId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
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
