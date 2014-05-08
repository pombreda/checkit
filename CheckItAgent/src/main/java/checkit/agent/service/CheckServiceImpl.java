package checkit.agent.service;

import checkit.agent.dao.CheckDAO;
import checkit.server.domain.Check;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CheckDAO checkDAO;
    
    @Override
    public List<Check> getCheckList() {
        return checkDAO.getCheckList();
    }

    @Override
    public void createCheck(Check check) {
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
