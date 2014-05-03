/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.Result;
import checkit.server.domain.Testing;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ResultService {
    public List<Result> getResultList(int testId);
    public List<Result> getResultListAsc(int testId);
    public void createResult(Result result);
    public void deleteResult(Result result);
    public void postStartTesting(Testing testing);
    public void postStopTesting(Testing testing);
    public List<Result> createResultGraphOutput(List<Result> results);
    public List<Result> getDataForLastDays(List<Result> results, long numberOfDays);
    public List<Long> getTimesForLastDays(List<Result> results, long numberOfDays);
    public List<Integer> getCountsForLastDays(List<Result> results, long numberOfDays);
    public int getRegularReportDownCount(List<Result> results);
    public long getRegularReportDownTime(List<Result> results);
}
