package checkit.server.service;

import checkit.server.domain.Result;
import checkit.server.domain.Checking;
import java.util.List;

public interface ResultService {
    public List<Result> getResultList(int checkId);
    public List<Result> getResultListAsc(int checkId);
    public void createResult(Result result);
    public void deleteResult(Result result);
    public void postStartChecking(Checking checking);
    public void postStopChecking(Checking checking);
    public List<Result> createResultGraphOutput(List<Result> results);
    public List<Result> getDataForLastDays(List<Result> results, long numberOfDays);
    public List<Long> getTimesForLastDays(List<Result> results, long numberOfDays);
    public List<Integer> getCountsForLastDays(List<Result> results, long numberOfDays);
    public int getRegularReportDownCount(List<Result> results);
    public long getRegularReportDownTime(List<Result> results);
}
