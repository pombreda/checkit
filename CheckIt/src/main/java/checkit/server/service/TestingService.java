/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.Test;
import checkit.server.domain.Testing;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface TestingService {
    public void createTesting(Testing testing);
    public void createTesting(Test test);
    public void updateTesting(Test test);
    public List<Testing> getTestingByUserId(int userId);
    public List<Testing> getTestingByAgentId(int agentId);
    public List<Testing> getTestingByTestId(int testId);
    public Testing getTestingByTestAndAgentId(int testId, int agentId);
    public List<Testing> getAllTesting();
    public void deleteTesting(Testing testing);
    public void deleteTesting(Test test);
    public boolean isAlreadyTested(int testId);
}
