/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ReportingService interface
 */

package checkit.server.service;

import checkit.server.domain.Reporting;
import java.util.List;

public interface ReportingService {
    public List<Reporting> getReportingListByUser(int userId);
    public List<Reporting> getReportingListByCheck(int checkId);
    public List<Reporting> getReportingListByContact(int contactId);
    public void createReporting(Reporting reporting);
    public void deleteReporting(Reporting reporting);
}
