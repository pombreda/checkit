package checkit.server.service;

import checkit.server.dao.ResultDAO;
import checkit.server.domain.Result;
import checkit.server.domain.Check;
import checkit.server.domain.Checking;
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
    private CheckService checkService;
    
    private final String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    
    @Override
    public List<Result> getResultList(int checkId) {
        return resultDAO.getResultList(checkId);
    }

    @Override
    public List<Result> getResultListAsc(int checkId) {
        return resultDAO.getResultListAsc(checkId);
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
    public void postStartChecking(Checking checking) {
        Result result = createResultFromChecking(checking);
        result.setStatus("R");
        createResult(result);
        
        Check check = checkService.getCheckById(checking.getCheckId());
        check.setChecked(false);
        checkService.updateCheck(check);
    }

    @Override
    public void postStopChecking(Checking checking) {
        Result result = createResultFromChecking(checking);
        result.setStatus("S");
        createResult(result);
    }
    
    private Result createResultFromChecking(Checking checking) {
	Date today = new java.util.Date();
	Date now = new Timestamp(today.getTime());

        Result result = new Result();
        result.setAgentId(checking.getAgentId());
        result.setCheckId(checking.getCheckId());
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
    
    private int[] addCountToActivity(String status, int[] currentValues) {
        switch (status) {
            case "N":
                currentValues[0]++;
                break;
            case "U":
                currentValues[1]++;
                break;
            case "D":
                currentValues[2]++;
                break;
        }
        return currentValues;
    }
    
    @Override
    public List<Result> getDataForLastDays(List<Result> results, long numberOfDays) {
        Date date = new Date();
        date.setTime(date.getTime() - numberOfDays*1000*60*60*24);
        
        Date resultDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String beginningStatus = "undefined";
        String lastStatus = "undefined";
        int length = results.size();
        for (int i=length-1; i>=0; i--) {
            try {
                if (lastStatus.equals("undefined")) {
                    lastStatus = results.get(i).getStatus();
                }
                resultDate = simpleDateFormat.parse(results.get(i).getTime());
                if (resultDate.compareTo(date) < 0) {
                    if (beginningStatus.equals("undefined")) {
                        beginningStatus = results.get(i).getStatus();
                    }
                    results.remove(i);
                }
            } catch (ParseException ex) {
            }
        }
        if (beginningStatus.equals("undefined")) {
            beginningStatus = "N";
        }

        //add beginning and last status to the left and right edge of the interval
        Result result = new Result();
        result.setTime(simpleDateFormat.format(date));
        result.setStatus(beginningStatus);
        results.add(0, result);

        result = new Result();
        result.setTime(simpleDateFormat.format(new Date()));
        result.setStatus(lastStatus);
        results.add(result);
        
        return results;
    }
    
    @Override
    public List<Long> getTimesForLastDays(List<Result> results, long numberOfDays) {
        //Return list of three items: 0 - do not measured, 1 - up, 2 - down
        //-1 for all data
        long[] output = new long[]{0L, 0L, 0L};
        Date date = new Date();
        if (numberOfDays >= 0) {
            date.setTime(date.getTime() - numberOfDays*1000*60*60*24);
        } else {
            try {
                date = new SimpleDateFormat(dateFormat).parse(results.get(0).getTime());
            } catch (ParseException ex) {}
        }
        Date resultDate;
        String currentStatus = "N";
        for (Result result : results) {
            try {
                resultDate = new SimpleDateFormat(dateFormat).parse(result.getTime());
                if (resultDate.compareTo(date) >= 0) {
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

    @Override
    public List<Integer> getCountsForLastDays(List<Result> results, long numberOfDays) {
        //Return list of three items: 0 - do not measured, 1 - up, 2 - down
        //-1 for all data
        int[] output = new int[]{0, 0, 0};
        Date date = new Date();
        if (numberOfDays >= 0) {
            date.setTime(date.getTime() - numberOfDays*1000*60*60*24);
        } else {
            try {
                date = new SimpleDateFormat(dateFormat).parse(results.get(0).getTime());
            } catch (ParseException ex) {}
        }
        Date resultDate;
        String currentStatus = "N";
        for (Result result : results) {
            try {
                resultDate = new SimpleDateFormat(dateFormat).parse(result.getTime());
                if (resultDate.compareTo(date) >= 0) {
                    output = addCountToActivity(currentStatus, output);
                }
                currentStatus = result.getStatus();
            } catch (ParseException ex) {
            }
        }
        
        List<Integer> outputList = new ArrayList<Integer>();
        for (int value : output) {
            outputList.add(value);
        }
        return outputList;
    }

    @Override
    public int getRegularReportDownCount(List<Result> results) {
        int downCount = getCountsForLastDays(results, 7).get(2);
        return downCount;
    }

    @Override
    public long getRegularReportDownTime(List<Result> results) {
        long downTime = getTimesForLastDays(results, 7).get(2);
        return downTime;
    }
}
