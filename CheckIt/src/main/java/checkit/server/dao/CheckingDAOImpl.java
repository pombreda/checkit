package checkit.server.dao;

import checkit.server.domain.Checking;
import checkit.server.jdbc.CheckingRowMapper;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CheckingDAOImpl implements CheckingDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public void createChecking(Checking checking) {
        String sql = "INSERT INTO checking (agent_id, check_id, user_id) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                checking.getAgentId(),
                checking.getCheckId(),
                checking.getUserId()
            }
        );
    }
    
    @Override
    public List<Checking> getCheckingByUserId(int userId) {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    @Override
    public List<Checking> getCheckingByAgentId(int agentId) {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking WHERE agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    @Override
    public List<Checking> getCheckingByCheckId(int checkId) {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking WHERE check_id=" + checkId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    @Override
    public Checking getCheckingByCheckAndAgentId(int checkId, int agentId) {
        List<Checking> checkingList = new ArrayList<Checking>();
        String sql = "SELECT * FROM checking WHERE check_id=" + checkId + " AND agent_id=" + agentId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        if (checkingList.isEmpty()) return null;
        return checkingList.get(0); 
    }

    @Override
    public List<Checking> getAllChecking() {
        List checkingList = new ArrayList();
        String sql = "SELECT * FROM checking";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        checkingList = jdbcTemplate.query(sql, new CheckingRowMapper());
        return checkingList;    
    }

    @Override
    public void deleteChecking(Checking checking) {
        String sql = "DELETE FROM checking WHERE check_id=" + checking.getCheckId() + " AND agent_id=" + checking.getAgentId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void updateAgentOfChecking(Checking checking, int agentId) {
        String sql = "UPDATE checking SET agent_id=? WHERE check_id=? AND agent_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                agentId,
                checking.getCheckId(),
                checking.getAgentId()
            }
        );    
    }

}
