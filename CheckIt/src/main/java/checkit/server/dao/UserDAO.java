package checkit.server.dao;

import checkit.server.domain.User;
import checkit.server.domain.UserActivation;
import java.util.List;

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
