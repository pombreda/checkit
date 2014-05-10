/**
 * @file
 * @author  Marek Dorda
 *
 * @section DESCRIPTION
 *
 * The UserDAO interface
 */

package checkit.server.dao;

import checkit.server.domain.User;
import checkit.server.domain.UserActivation;
import java.util.List;

public interface UserDAO {
    public void createUser(User user);
    public List<User> getUserList();
    public void updateUser(User user);
    public void deleteUser(int userId);
    public void requestActivation(int userId, String hash, String email);
    public UserActivation getUserActivationByHash(String hash);
    public void deleteUserActivationRequest(int userId);
    public User getUserById(int userId);
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
}
