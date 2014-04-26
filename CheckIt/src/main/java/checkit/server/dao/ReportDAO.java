/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.Report;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ReportDAO {
    public List<Report> getReportListByUser(int userId);
    public List<Report> getReportListByTest(int testId);
    public List<Report> getReportListByContact(int contactId);
    public void createReport(Report report);
    public void deleteReport(Report report);
}
