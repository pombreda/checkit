/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.dao.ResultDAO;
import checkit.server.domain.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultDAO resultDAO;
    
    @Override
    public List<Result> getResultList(int testId) {
        return resultDAO.getResultList(testId);
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
