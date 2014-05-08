package checkit.server.service;

import checkit.server.domain.User;
import java.util.List;

public interface UserService {
    public void createUser(User user);
    public List<User> getUserList();
    public void deleteUser(int id);
    public User getUserById(int id);
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
    public void updateUser(User user);
    public void updateUserEmail(User user, String email);
    public void updateUserPassword(User user, String password);
    public boolean confirmEmail(String hash);
}
