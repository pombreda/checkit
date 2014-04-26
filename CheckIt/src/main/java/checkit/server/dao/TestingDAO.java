/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Testing;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface TestingDAO {
    public void createTesting(Testing testing);
    public List<Testing> getTestingByUserId(int userId);
    public List<Testing> getTestingByAgentId(int agentId);
    public List<Testing> getTestingByTestId(int testId);
    public List<Testing> getTestingByTestAndAgentId(int testId, int agentId);
    public List<Testing> getAllTesting();
    public void deleteTesting(Testing testing);
    public void updateAgentOfTesting(Testing testing, int agentId);
}
