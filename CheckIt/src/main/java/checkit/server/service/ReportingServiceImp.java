/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ReportingService implementation
 * All services related to reporting
 */

package checkit.server.service;

import checkit.plugin.component.PluginReportComponent;
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
    private PluginReportComponent pluginService;
    
    @Autowired
    private ContactDetailService contactDetailService;
    
    /**
     * Get the list of all reportings belong to user
     *
     * @param userId Id of user
     *
     * @return List of all reporting belong to user.
     */
    @Override
    public List<Reporting> getReportingListByUser(int userId) {
        return reportingDAO.getReportingListByUser(userId);
    }

    /**
     * Get the list of all reportings belong to check
     *
     * @param checkId Id of check
     *
     * @return List of all reporting belong to check.
     */
    @Override
    public List<Reporting> getReportingListByCheck(int checkId) {
        return reportingDAO.getReportingListByCheck(checkId);
    }

    /**
     * Get the list of all reportings belong to contact
     *
     * @param contactId Id of contact
     *
     * @return List of all reporting belong to contact.
     */
    @Override
    public List<Reporting> getReportingListByContact(int contactId) {
        return reportingDAO.getReportingListByContact(contactId);
    }

    /**
     * Create new reporting
     *
     * @param reporting Reporting to create
     */
    @Override
    public void createReporting(Reporting reporting) {
        reportingDAO.createReporting(reporting);
    }

    /**
     * Delete reporting
     *
     * @param reporting Reporting to delete
     */
    @Override
    public void deleteReporting(Reporting reporting) {
        reportingDAO.deleteReporting(reporting);
    }
    
    /**
     * Cron, sends every week on Sunday at 6PM regular reports.
     *
     */
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
