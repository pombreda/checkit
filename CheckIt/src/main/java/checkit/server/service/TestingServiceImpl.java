/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.dao.AgentDAO;
import checkit.server.dao.TestingDAO;
import checkit.server.domain.Agent;
import checkit.server.domain.Test;
import checkit.server.domain.Testing;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestingServiceImpl implements TestingService {
    @Autowired
    private TestingDAO testingDAO;
    
    @Autowired
    private AgentDAO agentDAO;

    @Autowired
    private NetworkService networkService;
    
    private int getIdOfLeastBusyAgent() {
        List<Agent> agents = agentDAO.getAgentList();

        int minId = agents.get(0).getAgentId();
        int minValue = testingDAO.getTestingByAgentId(minId).size();

        int currentValue;

        for (Agent agent : agents) {
            currentValue = testingDAO.getTestingByAgentId(agent.getAgentId()).size();
            if (currentValue < minValue) {
                minValue = currentValue;
                minId = agent.getAgentId();
            }
        }
        return minId;
    }
    
    @Override
    public void createTesting(Testing testing) {
        networkService.postCreatingToAgent(testing);
        testingDAO.createTesting(testing);
    }

    @Override
    public void createTesting(Test test) {
        Testing testing = new Testing();
        testing.setTestId(test.getTestId());
        testing.setUserId(test.getUserId());
        testing.setAgentId(getIdOfLeastBusyAgent());
        createTesting(testing);
    }

    @Override
    public List<Testing> getTestingByUserId(int userId) {
        return testingDAO.getTestingByUserId(userId);
    }

    @Override
    public List<Testing> getTestingByAgentId(int agentId) {
        return testingDAO.getTestingByAgentId(agentId);
    }

    @Override
    public List<Testing> getTestingByTestId(int testId) {
        return testingDAO.getTestingByTestId(testId);
    }

    @Override
    public Testing getTestingByTestAndAgentId(int testId, int agentId) {
        return testingDAO.getTestingByTestAndAgentId(testId, agentId);
    }

    @Override
    public List<Testing> getAllTesting() {
        return testingDAO.getAllTesting();
    }

    @Override
    public void deleteTesting(Testing testing) {
        testingDAO.deleteTesting(testing);
    }

    @Override
    public void deleteTesting(Test test) {
        List<Testing> testingList = testingDAO.getTestingByTestId(test.getTestId());
        Testing testingObject = new Testing();
        testingObject.setTestId(test.getTestId());
        testingObject.setUserId(test.getUserId());
        for (Testing testing : testingList) {
            testingObject.setAgentId(testing.getAgentId());
            networkService.postDeletingToAgent(testing);
            testingDAO.deleteTesting(testing);
        }
    }

    @Override
    public boolean isAlreadyTested(int testId) {
        return testingDAO.getTestingByTestId(testId).size() > 0;
    }
    
}
