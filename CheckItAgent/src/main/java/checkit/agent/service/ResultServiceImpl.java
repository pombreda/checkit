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
    
    @Override
    public List<Result> getResultList() {
        return resultDAO.getResultList();
    }

    @Override
    public void createResult(Result result) {
        resultDAO.createResult(result);
    }

    @Override
    public void deleteResult(Result result) {
        resultDAO.deleteResult(result);
    }
    
}
