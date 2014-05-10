/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckingService interface
 */

package checkit.server.service;

import checkit.server.domain.Check;
import checkit.server.domain.Checking;
import java.util.List;

public interface CheckingService {
    public void createChecking(Checking checking);
    public void createChecking(Check check);
    public void updateChecking(Check check);
    public List<Checking> getCheckingByUserId(int userId);
    public List<Checking> getCheckingByAgentId(int agentId);
    public List<Checking> getCheckingByCheckId(int checkId);
    public Checking getCheckingByCheckAndAgentId(int checkId, int agentId);
    public List<Checking> getAllChecking();
    public void deleteChecking(Checking checking);
    public void deleteChecking(Check check);
    public boolean isAlreadyChecked(int checkId);
}
