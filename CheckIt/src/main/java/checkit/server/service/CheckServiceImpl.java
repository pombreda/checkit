/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckService implementation
 * All services related to check
 */

package checkit.server.service;

import checkit.plugin.domain.Input;
import checkit.plugin.component.PluginCheckComponent;
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
    private PluginCheckComponent pluginCheckService;
    
    /**
     * Get the list of all checks belong to user
     *
     * @param userId Id of user
     *
     * @return List of all checks belong to user.
     */
    @Override
    public List<Check> getCheckList(int userId) {
        return checkDAO.getCheckList(userId);
    }

    /**
     * Get the list of all checks contained in reportings
     *
     * @param reporting List of reportings
     *
     * @return List of all checks contained in reportings.
     */
    @Override
    public List<Check> getCheckListByReporting(List<Reporting> reporting) {
        List<Check> result = new ArrayList();
        for (Reporting item : reporting) {
            result.add(getCheckById(item.getCheckId()));
        }
        return result;
    }
    
    /**
     * Create new check
     *
     * @param check Check to create
     */
    @Override
    public void createCheck(Check check) {
        String plugin = check.getFilename();
        Object instance = pluginCheckService.getPluginInstance(plugin);
        List<Input> inputs = pluginCheckService.getInputs(instance);
        String dataInitJSON = pluginCheckService.getInitEmptyDataJSON(inputs);
        check.setData(dataInitJSON);
        checkDAO.createCheck(check);
    }

    /**
     * Delete check
     *
     * @param checkId Id of check to delete
     */
    @Override
    public void deleteCheck(int checkId) {
        checkDAO.deleteCheck(checkId);
    }

    /**
     * Update check
     *
     * @param check Check to update, which already includes the updated data.
     */
    @Override
    public void updateCheck(Check check) {
        checkDAO.updateCheck(check);
    }

    /**
     * Get check by check id
     *
     * @param checkId Id of check to get
     *
     * @return Check or null if not exists.
     */
    @Override
    public Check getCheckById(int checkId) {
        return checkDAO.getCheckById(checkId);
    }

}
