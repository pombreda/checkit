package checkit.server.service;

import checkit.server.component.EmailComponent;
import checkit.server.component.PasswordComponent;
import checkit.server.dao.UserDAO;
import checkit.server.domain.User;
import checkit.server.domain.UserActivation;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private PasswordComponent passwordService;
    
    @Autowired
    private EmailComponent emailService;
    
    @Override
    public void createUser(User user) {
        String encodedPassword = passwordService.encodePassword(user);
        user.setPassword(encodedPassword);
        String activationCode = emailService.getActivationCode(user);
        userDAO.createUser(user);
        user = userDAO.getUserByUsername(user.getUsername()); //need user's ID after store in DB
        userDAO.requestActivation(user.getUserId(), activationCode, user.getEmail());
        emailService.sendActivationLink(user, activationCode);
    }  

    @Override
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public boolean confirmEmail(String hash) {
        UserActivation userActivation = userDAO.getUserActivationByHash(hash);
        if (userActivation == null) return false;
        userDAO.deleteUserActivationRequest(userActivation.getId());
        User user = userDAO.getUserById(userActivation.getId());
        user.setEmail(userActivation.getEmail());
        user.setEnabled(true);
        updateUser(user);
        return true;
    }

    @Override
    public void updateUserEmail(User user, String email) {
        String activationCode = emailService.getActivationCode(user);
        userDAO.requestActivation(user.getUserId(), activationCode, email);
        user.setEmail(email);
        emailService.sendUpdateLink(user, activationCode);        
    }

    @Override
    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
        String encodedPassword = passwordService.encodePassword(user);
        user.setPassword(encodedPassword);
        updateUser(user);
    }

    @Override
    public User getLoggedUser(Principal principal) {
        if (principal == null) return null;
        String username = principal.getName();
        User user = getUserByUsername(username);
        return user;
    }
}