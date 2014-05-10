/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ResultService interface
 */

package checkit.agent.service;

import checkit.server.domain.Result;
import java.util.List;

public interface ResultService {
    public List<Result> getResultList();
    public void createResult(Result result);
    public void deleteResult(Result result);
}
