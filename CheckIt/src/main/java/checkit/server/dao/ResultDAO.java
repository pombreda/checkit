package checkit.server.dao;

import checkit.server.domain.Result;
import java.util.List;

public interface ResultDAO {
    public List<Result> getResultList(int checkId);
    public List<Result> getResultListAsc(int checkId);
    public void createResult(Result result);
    public void deleteResult(Result result);
}
