/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.dao;

import checkit.server.domain.User;
import checkit.server.domain.UserActivation;
import java.util.List;

/**
 *
 * @author Dodo
 */
public interface UserDAO {
    public void createUser(User user);
    public List<User> getUserList(); //*
    public void updateUser(User user);
    public void deleteUser(int id);
    public void requestActivation(int id, String hash, String email);
    public UserActivation getUserActivationByHash(String hash);
    public void deleteUserActivationRequest(int id);
    public User getUserById(int id);
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
}
