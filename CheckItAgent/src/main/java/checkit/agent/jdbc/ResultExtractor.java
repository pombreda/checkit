package checkit.agent.jdbc;

import checkit.server.domain.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ResultExtractor implements ResultSetExtractor<Result> {
    @Override
    public Result extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Result result = new Result();
        result.setCheckId(resultSet.getInt(1));
        
        result.setTime(resultSet.getString(2));
/*
        try {
            result.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(resultSet.getString(2)));
        } catch (ParseException ex) {
            
        }
*/        
        result.setStatus(resultSet.getString(3));
        result.setData(resultSet.getString(4));
        return result;
    }
}
