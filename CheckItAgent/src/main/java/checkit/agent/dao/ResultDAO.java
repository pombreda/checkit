package checkit.agent.dao;

import checkit.server.domain.Result;
import java.util.List;

public interface ResultDAO {
    public List<Result> getResultList();
    public void createResult(Result result);
    public void deleteResult(Result result);
}
