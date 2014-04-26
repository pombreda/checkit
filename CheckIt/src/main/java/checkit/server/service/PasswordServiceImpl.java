/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dodo
 */
@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Override
    public boolean isPasswordStrong(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*\\d.*")) return false;
        if (password.matches(".*\\s.*$")) return false;
        return true;
    }

    @Override
    public String encodePassword(User user) {
        return passwordEncoder.encodePassword(user.getPassword(), user.getUsername());

    }
    
}
