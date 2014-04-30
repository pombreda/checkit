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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<Result> getResultListAsc(int testId) {
        return resultDAO.getResultListAsc(testId);
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
        
        Test test = testService.getTestById(testing.getTestId());
        test.setChecked(false);
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
    
    

    @Override
    public List<Result> createResultGraphOutput(List<Result> results) {
        //Sets status of "run" action from following status and the following one removes (R, U, S -> U, S     R, D, U, D, U -> D, U, D, U)
        //Also changes all S statuses and R statuses without following U or D to N - Not measured
        for (int i=0; i < results.size(); i++) {
            switch (results.get(i).getStatus()) {
                case "R":
                    if (i < results.size() - 1 && (results.get(i+1).getStatus().equals("D") || results.get(i+1).getStatus().equals("U"))) {
                        results.get(i).setStatus(results.get(i+1).getStatus());
                        results.remove(i+1);
                    } else {
                        results.get(i).setStatus("N");
                    } break;
                case "S":
                    results.get(i).setStatus("N");
                    break;
            }
        }
        return results;
    }

    private long[] addTimeToActivity(String status, Date resultDate, Date date, long[] currentValues) {
        switch (status) {
            case "N":
                currentValues[0] += (resultDate.getTime()-date.getTime())/1000;
                break;
            case "U":
                currentValues[1] += (resultDate.getTime()-date.getTime())/1000;
                break;
            case "D":
                currentValues[2] += (resultDate.getTime()-date.getTime())/1000;
                break;
        }
        return currentValues;
    }
    
    @Override
    public List<Long> getChartData(List<Result> results, long numberOfDays) {
        //Return list of three items: 0 - do not measured, 1 - up, 2 - down
        long[] output = new long[]{0L, 0L, 0L};
        Date date = new Date();
        date.setTime(date.getTime() - numberOfDays*1000*60*60*24);
        Date resultDate;
        String currentStatus = "N";
        for (Result result : results) {
            try {
                resultDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(result.getTime());
                if (resultDate.after(date)) {
                    output = addTimeToActivity(currentStatus, resultDate, date, output);
                    date = resultDate;
                }
                currentStatus = result.getStatus();
            } catch (ParseException ex) {
            }
        }
        Date current = new Date();
        output = addTimeToActivity(currentStatus, current, date, output);
        
        List<Long> outputList = new ArrayList<Long>();
        for (long value : output) {
            outputList.add(value);
        }
        return outputList;
    }
}
