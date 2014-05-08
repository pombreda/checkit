package checkit.server.dao;

import checkit.server.domain.Checking;
import java.util.List;

public interface CheckingDAO {
    public void createChecking(Checking checking);
    public List<Checking> getCheckingByUserId(int userId);
    public List<Checking> getCheckingByAgentId(int agentId);
    public List<Checking> getCheckingByCheckId(int checkId);
    public Checking getCheckingByCheckAndAgentId(int checkId, int agentId);
    public List<Checking> getAllChecking();
    public void deleteChecking(Checking checking);
    public void updateAgentOfChecking(Checking checking, int agentId);
}
