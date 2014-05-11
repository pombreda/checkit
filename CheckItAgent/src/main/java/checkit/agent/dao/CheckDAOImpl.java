/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckDAO implementation
 */

package checkit.agent.dao;

import checkit.server.domain.Check;
import checkit.agent.jdbc.CheckRowMapper;
import checkit.global.component.JsonComponent;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CheckDAOImpl implements CheckDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JsonComponent jsonService;

    /**
     * Get the list of all rows (checks) in database
     *
     * @return List of all checks.
     */
    @Override
    public List<Check> getCheckList() {
        List checkList = new ArrayList();
        String sql = "SELECT * FROM checks";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkList = jdbcTemplate.query(sql, new CheckRowMapper());
        return checkList;    
    }

    /**
     * Create new row (check) in database
     *
     * @param check Check for insertion into the database
     */
    @Override
    public void createCheck(Check check) {
        String sql = "INSERT INTO checks (check_id, data, filename, interval) VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                check.getCheckId(),
                jsonService.stringToJSON(check.getData()),
                check.getFilename(),
                check.getInterval()
            }
        );
    }

    /**
     * Delete row (check) in database
     *
     * @param checkId Id of check to delete
     */
    @Override
    public void deleteCheck(int checkId) {
        String sql = "DELETE FROM checks WHERE check_id=" + checkId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (check) in database.
     * Get check id and user id and rewrite all other data.
     *
     * @param check Check to update
     */
    @Override
    public void updateCheck(Check check) {
        String sql = "UPDATE checks SET data=?, interval=? WHERE check_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                jsonService.stringToJSON(check.getData()),
                check.getInterval(),
                check.getCheckId()
            }
        );
    }

    /**
     * Get row (check) by check id
     *
     * @param checkId Id of check to get
     *
     * @return Check or null if not exists.
     */
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
