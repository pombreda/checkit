/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckService interface
 */

package checkit.server.service;

import checkit.server.domain.Reporting;
import checkit.server.domain.Check;
import java.util.List;

public interface CheckService {
    public List<Check> getCheckList(int userId);
    public List<Check> getCheckListByReporting(List<Reporting> reporting);
    public void createCheck(Check check);
    public void deleteCheck(int checkId);
    public void updateCheck(Check check);
    public Check getCheckById(int checkId);
}
