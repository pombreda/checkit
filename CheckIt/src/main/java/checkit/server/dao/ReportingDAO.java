/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ReportingDAO interface
 */

package checkit.server.dao;

import checkit.server.domain.Reporting;
import java.util.List;

public interface ReportingDAO {
    public List<Reporting> getReportingListByUser(int userId);
    public List<Reporting> getReportingListByCheck(int checkId);
    public List<Reporting> getReportingListByContact(int contactId);
    public void createReporting(Reporting reporting);
    public void deleteReporting(Reporting reporting);
}
