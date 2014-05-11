/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The CheckDAO implementation
 */

package checkit.server.dao;

import checkit.global.component.JsonComponent;
import checkit.server.domain.Check;
import checkit.server.jdbc.CheckRowMapper;
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
     * Get the list of all rows (checks) belong to user id in database
     *
     * @param userId Id of user
     *
     * @return List of all checks belong to user.
     */
    @Override
    public List<Check> getCheckList(int userId) {
        List checkList = new ArrayList();
        String sql = "SELECT * FROM checks WHERE user_id=" + userId;
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
        String sql = "INSERT INTO checks (title, data, enabled, user_id, filename, interval, ok, checked) VALUES (?, ?, ?, ?, ?, ?, TRUE, FALSE)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                check.getTitle(),
                jsonService.stringToJSON(check.getData()),
                check.isEnabled(),
                check.getUserId(),
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
        String sql = "UPDATE checks SET title=?, data=?, enabled=?, interval=?, ok=?, checked=? WHERE check_id=? AND user_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                check.getTitle(),
                jsonService.stringToJSON(check.getData()),
                check.isEnabled(),
                check.getInterval(),
                check.isOk(),
                check.isChecked(),
                check.getCheckId(),
                check.getUserId()
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
