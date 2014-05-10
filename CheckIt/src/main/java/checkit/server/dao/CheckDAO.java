/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckDAO interface
 */

package checkit.server.dao;

import checkit.server.domain.Check;
import java.util.List;

public interface CheckDAO {
    public List<Check> getCheckList(int userId);
    public void createCheck(Check check);
    public void deleteCheck(int checkId);
    public void updateCheck(Check check);
    public Check getCheckById(int checkId);
}
