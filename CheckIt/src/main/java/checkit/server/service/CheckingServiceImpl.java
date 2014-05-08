package checkit.server.service;

import checkit.server.dao.AgentDAO;
import checkit.server.dao.CheckingDAO;
import checkit.server.domain.Agent;
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
    private AgentDAO agentDAO;

    @Autowired
    private NetworkService networkService;
    
    @Autowired
    private ResultService resultService;
    
    private int getIdOfLeastBusyAgent() {
        List<Agent> agents = agentDAO.getAgentList();

        int minId = agents.get(0).getAgentId();
        int minValue = checkingDAO.getCheckingByAgentId(minId).size();

        int currentValue;

        for (Agent agent : agents) {
            currentValue = checkingDAO.getCheckingByAgentId(agent.getAgentId()).size();
            if (currentValue < minValue) {
                minValue = currentValue;
                minId = agent.getAgentId();
            }
        }
        return minId;
    }
    
    @Override
    public void createChecking(Checking checking) {
        networkService.postCreatingToAgent(checking);
        resultService.postStartChecking(checking);
        checkingDAO.createChecking(checking);
    }

    @Override
    public void createChecking(Check check) {
        Checking checking = new Checking();
        checking.setCheckId(check.getCheckId());
        checking.setUserId(check.getUserId());
        checking.setAgentId(getIdOfLeastBusyAgent());
        createChecking(checking);
    }

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

    @Override
    public List<Checking> getCheckingByUserId(int userId) {
        return checkingDAO.getCheckingByUserId(userId);
    }

    @Override
    public List<Checking> getCheckingByAgentId(int agentId) {
        return checkingDAO.getCheckingByAgentId(agentId);
    }

    @Override
    public List<Checking> getCheckingByCheckId(int checkId) {
        return checkingDAO.getCheckingByCheckId(checkId);
    }

    @Override
    public Checking getCheckingByCheckAndAgentId(int checkId, int agentId) {
        return checkingDAO.getCheckingByCheckAndAgentId(checkId, agentId);
    }

    @Override
    public List<Checking> getAllChecking() {
        return checkingDAO.getAllChecking();
    }

    @Override
    public void deleteChecking(Checking checking) {
        checkingDAO.deleteChecking(checking);
    }

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

    @Override
    public boolean isAlreadyChecked(int checkId) {
        return checkingDAO.getCheckingByCheckId(checkId).size() > 0;
    }
    
}
