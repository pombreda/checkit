/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.agent.service;

import checkit.server.domain.Result;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface ResultService {
    public List<Result> getResultList();
    public void createResult(Result result);
    public void deleteResult(Result result);
}
