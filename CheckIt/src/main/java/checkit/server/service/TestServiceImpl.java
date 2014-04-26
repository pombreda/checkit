/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.plugin.service.PluginCheckService;
import checkit.server.dao.TestDAO;
import checkit.server.domain.Report;
import checkit.server.domain.Test;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDAO testDAO;
    
    @Autowired
    private PluginCheckService pluginCheckService;
    
    @Override
    public List<Test> getTestList(int userId) {
        return testDAO.getTestList(userId);
    }

    @Override
    public List<Test> getTestListByReport(List<Report> report) {
        List<Test> result = new ArrayList();
        for (Report item : report) {
            result.add(getTestById(item.getTestId()));
        }
        return result;
    }
    
    @Override
    public void createTest(Test test) {
        String plugin = test.getPluginFilename();
        Object instance = pluginCheckService.getPluginInstance(plugin);
        List< List<String> > inputs = pluginCheckService.getInputs(instance);
        String dataInitJSON = pluginCheckService.getInitEmptyDataJSON(inputs);
        test.setData(dataInitJSON);
        testDAO.createTest(test);
    }

    @Override
    public void deleteTest(int testId) {
        testDAO.deleteTest(testId);
    }

    @Override
    public void updateTest(Test test) {
        testDAO.updateTest(test);
    }

    @Override
    public Test getTestById(int testId) {
        return testDAO.getTestById(testId);
    }

}
