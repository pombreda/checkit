/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.dao.ReportDAO;
import checkit.server.domain.Report;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImp implements ReportService {
    @Autowired
    private ReportDAO reportDAO;
    
    @Override
    public List<Report> getReportListByUser(int userId) {
        return reportDAO.getReportListByUser(userId);
    }

    @Override
    public List<Report> getReportListByTest(int testId) {
        return reportDAO.getReportListByTest(testId);
    }

    @Override
    public List<Report> getReportListByContact(int contactId) {
        return reportDAO.getReportListByContact(contactId);
    }

    @Override
    public void createReport(Report report) {
        reportDAO.createReport(report);
    }

    @Override
    public void deleteReport(Report report) {
        reportDAO.deleteReport(report);
    }
    
}
