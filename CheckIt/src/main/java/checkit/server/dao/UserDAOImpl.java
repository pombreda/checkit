/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The AgentDAO implementation
 */

package checkit.server.dao;

import checkit.server.domain.User;
import checkit.server.domain.UserActivation;
import checkit.server.jdbc.UserActivationRowMapper;
import checkit.server.jdbc.UserRowMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private DataSource dataSource;
    
    /**
     * Get current timestamp
     *
     * @return Current timestamp.
     */
    private static Timestamp getCurrentTimestamp() {
	Date today = new java.util.Date();
	return new Timestamp(today.getTime());
    }
    
    /**
     * Create new row (user) in database
     *
     * @param user User for insertion into the database
     */
    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, user_role_id, password, email, enabled, created) VALUES (?, 1, ?, ?, FALSE, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                getCurrentTimestamp()
            }
        );
    }
  
    /**
     * Get the list of all rows (users) from database
     *
     * @return List of all users.
     */
    @Override
    public List<User> getUserList() {
        List userList = new ArrayList();
        String sql = "SELECT * FROM users";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        return userList;
    }

    /**
     * Delete row (user) in database
     *
     * @param userId Id of user to delete
     */
    @Override
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    /**
     * Update row (user) in database.
     * Get user id and rewrite all other data.
     *
     * @param user User to update
     */
    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET username=?, user_role_id=?, password=?, email=?, enabled=? WHERE user_id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                user.getUsername(),
                user.getUserRoleId(),
                user.getPassword(),
                user.getEmail(),
                user.isEnabled(),
                user.getUserId()
            }
        );
    }

    /**
     * Get row (user) by user id
     *
     * @param userId Id of user to get
     *
     * @return User or null if not exists.
     */
    @Override
    public User getUserById(int userId) {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        if (userList.isEmpty()) return null;
        return userList.get(0);
    }

    /**
     * Get row (user) by username
     *
     * @param username Username to get
     *
     * @return User or null if not exists.
     */
    @Override
    public User getUserByUsername(String username) {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE username='" + username + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        if (userList.isEmpty()) return null;
        return userList.get(0);
    }

    /**
     * Get row (user) by user email
     *
     * @param email Email of user to get
     *
     * @return User or null if not exists.
     */
    @Override
    public User getUserByEmail(String email) {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE email='" + email + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        if (userList.isEmpty()) return null;
        return userList.get(0);
    }

    /**
     * Create new row (userActivation) in database
     *
     * @param userId Id of user
     * @param hash Hash of request
     * @param email Email of user
     */
    @Override
    public void requestActivation(int userId, String hash, String email) {
        String sql = "INSERT INTO user_activation (user_activation_id, user_id, email) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                hash,
                userId,
                email
            }
        );
    }

    /**
     * Get row (userActivation) by hash
     *
     * @param hash Hash of activation to get
     *
     * @return User activation or null if not exists.
     */
    @Override
    public UserActivation getUserActivationByHash(String hash) {
        List<UserActivation> userActivation = new ArrayList<UserActivation>();
        String sql = "SELECT * FROM user_activation WHERE user_activation_id='" + hash + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userActivation = jdbcTemplate.query(sql, new UserActivationRowMapper());
        if (userActivation.isEmpty()) return null;
        return userActivation.get(0);
    }

    /**
     * Delete row (userActivation) in database
     *
     * @param userId Id of user activation to delete
     */
    @Override
    public void deleteUserActivationRequest(int userId) {
        String sql = "DELETE FROM user_activation WHERE user_id=" + userId;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
}
