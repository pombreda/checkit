/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import checkit.agent.dao.TestDAO;
import checkit.server.domain.Test;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDAO testDAO;
    
    @Override
    public List<Test> getTestList(int userId) {
        return testDAO.getTestList(userId);
    }

    @Override
    public void createTest(Test test) {
        testDAO.createTest(test);
    }

    @Override
    public void deleteTest(int testId) {
        testDAO.deleteTest(testId);
    }

    @Override
    public void updateTest(Test test) {
        testDAO.updateTest(test);
    }

    @Override
    public Test getTestById(int testId) {
        return testDAO.getTestById(testId);
    }

}
