/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Dodo
 */
@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private DataSource dataSource;
    
    private static java.sql.Timestamp getCurrentTimestamp() {
	Date today = new java.util.Date();
	return new Timestamp(today.getTime());
    }
    
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
  
    @Override
    public List<User> getUserList() {
        List userList = new ArrayList();
        String sql = "SELECT * FROM users";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        return userList;
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE user_id=" + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }

    @Override
    public void requestActivation(int id, String hash, String email) {
        String sql = "INSERT INTO user_activation (user_activation_id, user_id, email) VALUES (?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(
            sql,
            new Object[] {
                hash,
                id,
                email
            }
        );
    }

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

    @Override
    public User getUserById(int id) {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE user_id=" + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        if (userList.isEmpty()) return null;
        return userList.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE username='" + username + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        if (userList.isEmpty()) return null;
        return userList.get(0);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM users WHERE email='" + email + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userList = jdbcTemplate.query(sql, new UserRowMapper());
        if (userList.isEmpty()) return null;
        return userList.get(0);
    }

    @Override
    public UserActivation getUserActivationByHash(String hash) {
        List<UserActivation> userActivation = new ArrayList<UserActivation>();
        String sql = "SELECT * FROM user_activation WHERE user_activation_id='" + hash + "'";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        userActivation = jdbcTemplate.query(sql, new UserActivationRowMapper());
        if (userActivation.isEmpty()) return null;
        return userActivation.get(0);
    }

    @Override
    public void deleteUserActivationRequest(int id) {
        String sql = "DELETE FROM user_activation WHERE user_id=" + id;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(sql);
    }
}
