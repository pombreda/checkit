/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The ResultService implementation
 * All services related to result
 */

package checkit.agent.service;

import checkit.agent.dao.ResultDAO;
import checkit.server.domain.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultDAO resultDAO;
    
    /**
     * Get the list of all results, sorted ascending by date
     *
     * @return List of all results, sorted ascending by date.
     */
    @Override
    public List<Result> getResultList() {
        return resultDAO.getResultList();
    }

    /**
     * Create new result
     *
     * @param result Result to create
     */
    @Override
    public void createResult(Result result) {
        resultDAO.createResult(result);
    }

    /**
     * Delete result
     *
     * @param result Id of result to delete
     */
    @Override
    public void deleteResult(Result result) {
        resultDAO.deleteResult(result);
    }
    
}
