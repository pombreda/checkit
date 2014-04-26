/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import checkit.server.domain.Test;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface TestService {
    public List<Test> getTestList();
    public void createTest(Test test);
    public void deleteTest(int testId);
    public void updateTest(Test test);
    public Test getTestById(int testId);
}
