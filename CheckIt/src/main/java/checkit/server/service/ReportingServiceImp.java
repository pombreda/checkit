package checkit.server.service;

import checkit.plugin.service.PluginReportService;
import checkit.server.dao.ReportingDAO;
import checkit.server.domain.ContactDetail;
import checkit.server.domain.Reporting;
import checkit.server.domain.Result;
import checkit.server.domain.Check;
import checkit.server.domain.Checking;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReportingServiceImp implements ReportingService {
    @Autowired
    private ReportingDAO reportingDAO;
    
    @Autowired
    private CheckService checkService;
    
    @Autowired
    private CheckingService checkingService;
    
    @Autowired
    private ResultService resultService;
    
    @Autowired
    private PluginReportService pluginService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
    @Override
    public List<Reporting> getReportingListByUser(int userId) {
        return reportingDAO.getReportingListByUser(userId);
    }

    @Override
    public List<Reporting> getReportingListByCheck(int checkId) {
        return reportingDAO.getReportingListByCheck(checkId);
    }

    @Override
    public List<Reporting> getReportingListByContact(int contactId) {
        return reportingDAO.getReportingListByContact(contactId);
    }

    @Override
    public void createReporting(Reporting reporting) {
        reportingDAO.createReporting(reporting);
    }

    @Override
    public void deleteReporting(Reporting reporting) {
        reportingDAO.deleteReporting(reporting);
    }
    
    @Scheduled(cron="0 0 18 ? * SUN")
    public void sendRegularReports() {
        List<Checking> checkingList = checkingService.getAllChecking();
        List<ContactDetail> contactDetailList;
        for (Checking checking : checkingList) {
            contactDetailList = contactDetailService.getContactDetailListByCheckIdWhereRegularIsActive(checking.getCheckId());
            if (!contactDetailList.isEmpty()) {
                Check check = checkService.getCheckById(checking.getCheckId());
                List<Result> results = resultService.getResultListAsc(check.getCheckId());
                int numberOfDowns = resultService.getRegularReportDownCount(results);
                long timeOfDowns = resultService.getRegularReportDownTime(results);
                pluginService.reportRegular(contactDetailList, check.getTitle(), numberOfDowns, timeOfDowns);
            }
        }
    }
    
}
