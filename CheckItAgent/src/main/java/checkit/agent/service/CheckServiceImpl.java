/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckService implementation
 * All services related to check
 */

package checkit.agent.service;

import checkit.agent.dao.CheckDAO;
import checkit.server.domain.Check;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CheckDAO checkDAO;
    
    /**
     * Get the list of all checks
     *
     * @return List of all checks.
     */
    @Override
    public List<Check> getCheckList() {
        return checkDAO.getCheckList();
    }

    /**
     * Create new check
     *
     * @param check Check to create
     */
    @Override
    public void createCheck(Check check) {
        checkDAO.createCheck(check);
    }

    /**
     * Delete check
     *
     * @param checkId Id of check to delete
     */
    @Override
    public void deleteCheck(int checkId) {
        checkDAO.deleteCheck(checkId);
    }

    /**
     * Update check
     *
     * @param check Check to update, which already includes the updated data.
     */
    @Override
    public void updateCheck(Check check) {
        checkDAO.updateCheck(check);
    }

    /**
     * Get check by check id
     *
     * @param checkId Id of check to get
     *
     * @return Check or null if not exists.
     */
    @Override
    public Check getCheckById(int checkId) {
        return checkDAO.getCheckById(checkId);
    }

}
