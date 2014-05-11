/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckingService implementation
 * All services related to checking
 */

package checkit.server.service;

import checkit.server.dao.CheckingDAO;
import checkit.server.domain.Check;
import checkit.server.domain.Checking;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingServiceImpl implements CheckingService {
    @Autowired
    private CheckingDAO checkingDAO;
    
    @Autowired
    private AgentService agentService;

    @Autowired
    private NetworkService networkService;
    
    @Autowired
    private ResultService resultService;
    
    /**
     * Create new checking
     *
     * @param checking Checking to create
     */
    @Override
    public void createChecking(Checking checking) {
        networkService.postCreatingToAgent(checking);
        resultService.postStartChecking(checking);
        checkingDAO.createChecking(checking);
    }

    /**
     * Create new checking
     *
     * @param check Check to create
     */
    @Override
    public void createChecking(Check check) {
        int agentId = agentService.getIdOfLeastBusyAgent();
        
        if (agentId > 0) {
            Checking checking = new Checking();
            checking.setCheckId(check.getCheckId());
            checking.setUserId(check.getUserId());
            checking.setAgentId(agentId);
            createChecking(checking);
        }
    }

    /**
     * Update checking by check
     *
     * @param check Check for updating checking, which already includes the updated data.
     */
    @Override
    public void updateChecking(Check check) {
        //update check only at agent side
        List<Checking> checkingList = checkingDAO.getCheckingByCheckId(check.getCheckId());
        Checking checkingObject = new Checking();
        checkingObject.setCheckId(check.getCheckId());
        checkingObject.setUserId(check.getUserId());
        for (Checking checking : checkingList) {
            checkingObject.setAgentId(checking.getAgentId());
            networkService.postUpdatingToAgent(checking);
        }
    }

    /**
     * Get checking by user id
     *
     * @param userId Id of user
     *
     * @return Checking or null if not exists.
     */
    @Override
    public List<Checking> getCheckingByUserId(int userId) {
        return checkingDAO.getCheckingByUserId(userId);
    }

    /**
     * Get checking by agent id
     *
     * @param agentId Id of agent
     *
     * @return Checking or null if not exists.
     */
    @Override
    public List<Checking> getCheckingByAgentId(int agentId) {
        return checkingDAO.getCheckingByAgentId(agentId);
    }

    /**
     * Get checking by check id
     *
     * @param checkId Id of agent
     *
     * @return Checking or null if not exists.
     */
    @Override
    public List<Checking> getCheckingByCheckId(int checkId) {
        return checkingDAO.getCheckingByCheckId(checkId);
    }

    /**
     * Get checking by check and agent id
     *
     * @param checkId Id of check
     * @param agentId Id of agent
     *
     * @return Checking or null if not exists.
     */
    @Override
    public Checking getCheckingByCheckAndAgentId(int checkId, int agentId) {
        return checkingDAO.getCheckingByCheckAndAgentId(checkId, agentId);
    }

    /**
     * Get the list of all checking
     *
     * @return List of all checking.
     */
    @Override
    public List<Checking> getAllChecking() {
        return checkingDAO.getAllChecking();
    }

    /**
     * Delete checking
     *
     * @param checking Checking to delete
     */
    @Override
    public void deleteChecking(Checking checking) {
        checkingDAO.deleteChecking(checking);
    }

    /**
     * Delete checking by check
     *
     * @param check Check for deleting checking
     */
    @Override
    public void deleteChecking(Check check) {
        List<Checking> checkingList = checkingDAO.getCheckingByCheckId(check.getCheckId());
        Checking checkingObject = new Checking();
        checkingObject.setCheckId(check.getCheckId());
        checkingObject.setUserId(check.getUserId());
        for (Checking checking : checkingList) {
            checkingObject.setAgentId(checking.getAgentId());
            networkService.postDeletingToAgent(checking);
            resultService.postStopChecking(checking);
            checkingDAO.deleteChecking(checking);
        }
    }

    /**
     * Verify if check is running
     *
     * @param checkId Id of check
     * 
     * @return true if check is running, otherwise false.
     */
    @Override
    public boolean isAlreadyChecked(int checkId) {
        return checkingDAO.getCheckingByCheckId(checkId).size() > 0;
    }
    
}
