package checkit.server.service;

import checkit.plugin.domain.Input;
import checkit.plugin.service.PluginCheckService;
import checkit.server.dao.CheckDAO;
import checkit.server.domain.Reporting;
import checkit.server.domain.Check;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CheckDAO checkDAO;
    
    @Autowired
    private PluginCheckService pluginCheckService;
    
    @Override
    public List<Check> getCheckList(int userId) {
        return checkDAO.getCheckList(userId);
    }

    @Override
    public List<Check> getCheckListByReporting(List<Reporting> reporting) {
        List<Check> result = new ArrayList();
        for (Reporting item : reporting) {
            result.add(getCheckById(item.getCheckId()));
        }
        return result;
    }
    
    @Override
    public void createCheck(Check check) {
        String plugin = check.getFilename();
        Object instance = pluginCheckService.getPluginInstance(plugin);
        List<Input> inputs = pluginCheckService.getInputs(instance);
        String dataInitJSON = pluginCheckService.getInitEmptyDataJSON(inputs);
        check.setData(dataInitJSON);
        checkDAO.createCheck(check);
    }

    @Override
    public void deleteCheck(int checkId) {
        checkDAO.deleteCheck(checkId);
    }

    @Override
    public void updateCheck(Check check) {
        checkDAO.updateCheck(check);
    }

    @Override
    public Check getCheckById(int checkId) {
        return checkDAO.getCheckById(checkId);
    }

}
