/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.plugin.service.PluginReportService;
import checkit.server.dao.ReportDAO;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Report;
import checkit.server.domain.Result;
import checkit.server.domain.Test;
import checkit.server.domain.Testing;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImp implements ReportService {
    @Autowired
    private ReportDAO reportDAO;
    
    @Autowired
    private TestService testService;
    
    @Autowired
    private TestingService testingService;
    
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private PluginReportService pluginService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
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
    
    @Scheduled(cron="0 0 18 ? * SUN")
    public void sendRegularReports() {
        List<Testing> testingList = testingService.getAllTesting();
        List<ContactDetail> contactDetailList;
        for (Testing testing : testingList) {
            contactDetailList = contactDetailService.getContactDetailListByTestIdWhereRegularIsActive(testing.getTestId());
            if (!contactDetailList.isEmpty()) {
                Test test = testService.getTestById(testing.getTestId());
                List<Result> results = resultService.getResultListAsc(test.getTestId());
                int numberOfDowns = resultService.getRegularReportDownCount(results);
                long timeOfDowns = resultService.getRegularReportDownTime(results);
                pluginService.reportRegular(contactDetailList, test.getTitle(), numberOfDowns, timeOfDowns);
            }
        }
    }
    
}
