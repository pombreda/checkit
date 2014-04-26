/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkit.server.service;

import checkit.server.domain.User;

/**
 *
 * @author Dodo
 */
public interface PasswordService {
    public boolean isPasswordStrong(String password);
    public String encodePassword(User user);
}
