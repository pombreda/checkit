package checkit.agent.dao;

import checkit.server.domain.Check;
import checkit.agent.jdbc.CheckRowMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CheckDAOImpl implements CheckDAO {
    @Autowired
    private DataSource dataSource;

    private PGobject stringToJSON(String jsonString) {
        PGobject json = new PGobject();
        json.setType("json");
        try {
            json.setValue(jsonString);
        } catch (SQLException ex) {
            Logger.getLogger(CheckDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    @Override
    public List<Check> getCheckList() {
        List checkList = new ArrayList();
        String sql = "SELECT * FROM checks";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkList = jdbcTemplate.query(sql, new CheckRowMapper());
        return checkList;    
    }

    @Override
    public void createCheck(Check check) {
        String sql = "INSERT INTO checks (check_id, data, filename, interval) VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                check.getCheckId(),
                stringToJSON(check.getData()),
                check.getFilename(),
                check.getInterval()
            }
        );
    }

    @Override
    public void deleteCheck(int checkId) {
        String sql = "DELETE FROM checks WHERE check_id=" + checkId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updateCheck(Check check) {
        String sql = "UPDATE checks SET data=?, interval=? WHERE check_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                stringToJSON(check.getData()),
                check.getInterval(),
                check.getCheckId()
            }
        );
    }

    @Override
    public Check getCheckById(int checkId) {
        List<Check> check = new ArrayList<Check>();
        String sql = "SELECT * FROM checks WHERE check_id=" + checkId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        check = jdbcTemplate.query(sql, new CheckRowMapper());
        if (check.isEmpty()) return null;
        return check.get(0);
    }

}
