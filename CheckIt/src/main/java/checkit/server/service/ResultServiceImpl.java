/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.dao.ResultDAO;
import checkit.server.domain.Result;
import checkit.server.domain.Test;
import checkit.server.domain.Testing;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultDAO resultDAO;
    
    @Autowired
    private TestService testService;
    
    @Override
    public List<Result> getResultList(int testId) {
        return resultDAO.getResultList(testId);
    }

    @Override
    public void createResult(Result result) {
        resultDAO.createResult(result);
    }

    @Override
    public void deleteResult(Result result) {
        resultDAO.deleteResult(result);
    }

    @Override
    public void postStartTesting(Testing testing) {
        Result result = createResultFromTesting(testing);
        result.setStatus("R");
        createResult(result);
        
        //The assumption that the service works when testing starts.
        Test test = testService.getTestById(testing.getTestId());
        test.setOk(true);
        testService.updateTest(test);
    }

    @Override
    public void postStopTesting(Testing testing) {
        Result result = createResultFromTesting(testing);
        result.setStatus("S");
        createResult(result);
    }
    
    private Result createResultFromTesting(Testing testing) {
	Date today = new java.util.Date();
	Date now = new Timestamp(today.getTime());

        Result result = new Result();
        result.setAgentId(testing.getAgentId());
        result.setTestId(testing.getTestId());
        result.setTime(now.toString());
        
        return result;
    }
}
